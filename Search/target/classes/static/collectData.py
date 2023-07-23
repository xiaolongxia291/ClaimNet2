import json
import re

from selenium import webdriver
from selenium.webdriver.common.by import By
from scrapy.selector import Selector


def init():
    # 1 初始化
    option = webdriver.EdgeOptions()
    # 这里添加edge的启动文件=>chrome的话添加chrome.exe的绝对路径
    option.binary_location = r'C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe'
    # 这里添加的是driver的绝对路径
    driver = webdriver.Edge(r'D:\setup\edgedriver_win64\msedgedriver.exe', options=option)
    return driver


# 爬取数据
def down(driver, patent_id, path):
    url = "https://worldwide.espacenet.com/patent/search?q=" + patent_id
    dict_claims = {
        "_id": patent_id,
        "_source": {}
    }

    # 2 获取元素
    try:
        driver.minimize_window()
        driver.get(url)
        driver.implicitly_wait(30)
        driver.find_element(by=By.CLASS_NAME, value='search')
        # time.sleep(1)
        driver.find_element(by=By.XPATH, value='//*[contains(@data-qa,"claimsTab_resultDescription")]').click()
        driver.find_element(by=By.XPATH, value='//*[contains(@class,"text-block__content--3_ryPSrw")]')
        # time.sleep(1)

        # 3 获取元素中的数据
        text = driver.find_element(by=By.XPATH, value='//body').get_attribute('innerHTML')
        html = Selector(text=text)
        text = html.xpath('//*[contains(@class, "text-block__content--3_ryPSrw")]')[0].xpath('string(.)').get()

        # 4 处理获取到的text
        pattern = r'[0-9]+\.\s'
        # text = '1.  A method of providing an interactive user interface (AW) of an interactive application simultaneously with a programme display (P) within a display area of a television receiver, wherein a proportion of the display area occupied by the interactive user interface (AW) increases, and a proportion of the display area occupied by the programme display (P) decreases, in response to user interaction with the interactive user interface (AW). 2.  The method of claim 1, wherein in a first state (S3) the interactive user interface (AW) comprises a plurality of menu items (M1...M4) in a first display area, and user selection of one of the menu items (M1...M4) causes the interactive user interface (AW) to enter a second state (S4) in which content relating to the selected menu item is displayed, in a second display area larger than the first display area.3.  The method of claim 2, wherein the content comprises one or more video content items (V1, V2).4.  The method of claim 3, wherein in the second state (S4) at least one said video content item (V1, V2) is output simultaneously with the programme display (P).5.  The method of claim 3, wherein in the second state (S4) one or more still images corresponding to the video content items (V1, V2) are output.6.  The method of any one of claims 3 to 5, wherein user selection of one of the video content items (V1, V2) causes the selected video content item (V1, V2) to be displayed (S5) without the programme display (P) within the display area.7.  The method of any preceding claim, wherein the interactive application is selected by the user from a menu of interactive applications (A1...A5) superimposed (S2) on the programme display (P).8.  A method of providing an interactive user interface (AW) within a display area of a television receiver, wherein an interactive application is selected by the user from a menu of interactive applications (A1...A5) superimposed (S2) on a programme (P) display within the television display area, and selection of the interactive application causes the interactive user interface (AW) of the selected application to be displayed (S3, S4) simultaneously with the programme display (P) within the display area.9.  The method of any preceding claim, wherein the interactive user interface (AW) does not overlap the programme display (P).10.  Apparatus arranged to perform the method of any preceding claim.11.  A computer program comprising program code arranged to perform the method of any one of claims 1 to 9.'
        list = re.split(pattern, text)
        for i in range(len(list)):
            if len(list[i]) != 0:
                dict_claims["_source"][str(i)]=list[i].strip()

        # 把爬取到的数据存放到path中
        if len(dict_claims["_source"])!=0:
            with open(file=path, mode='a', encoding='utf-8') as f:
                f.write(json.dumps(dict_claims) + "\n")
                print("【获取成功】")
    except Exception as e:
        # open(file='cited_ids_balanced_failed.txt', mode='a', encoding="utf-8").write(patent_id.strip()+"\n")
        print(e)
        pass


if __name__ == '__main__':
    f_read = open(file='../2 读取专利号/cited_ids_balanced_failed.txt', mode='r', encoding="utf-8")
    path_original = 'data_cited.json'
    driver = init()
    for line in f_read:
        patent_id = line.strip()
        print(patent_id)
        down(driver, patent_id, path_original)
