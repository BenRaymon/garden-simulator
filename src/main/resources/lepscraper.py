import json
import requests
from bs4 import BeautifulSoup
import time
import csv


if __name__ == "__main__":

    print("Reading plants.json file")
    plants = open("plants.json").read()
    print("Getting all lep names")
    with open("short-leps_data_vbon.csv", "r") as lep_f:
        csv_r = csv.DictReader(lep_f)
        lep_d = [x for x in csv_r]

    # list of unique lep names
    lep_n = []

    # filter out the empty slots
    print("Filtering empty spaces")
    for d in lep_d:
        if d['Lepidoptera Name'] != '' and d['Hostplant Name'] in plants:
            lep_n.append(d["Lepidoptera Name"])


    print("Starting image search\n------------------------------------------")

    #Dictionary will hold all lep name to url pairs
    lepDict = {

        "Lep Name":"Lep Url"
    }

    
    
    for lep in lep_n:
        lepName = lep
        url = "https://commons.wikimedia.org/w/index.php?search=" + lepName + "&title=Special:MediaSearch&go=Go&type=image"
        page = requests.get(url)
        soup = BeautifulSoup(page.content, 'html.parser')
        content = soup.find(id="content")
        firstImg = content.find("img",class_="wbmi-image")
        url = "None"
        if(firstImg != None):
            url = firstImg['src']
        lepDict[lepName] = url
        print("Lep = " + lepName + " URL = " + url)
        print("----------------------------------")
    
    
    print("Writing dict to file")
    w = csv.writer(open("LepImages.csv", "w"))
    for key, val in lepDict.items():
        w.writerow([key, val])

