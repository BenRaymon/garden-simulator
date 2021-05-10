import csv
import requests
import shutil
import time
from bs4 import BeautifulSoup




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


for lep in lep_n:
    try:
        species = lep
        url = "https://www.butterfliesandmoths.org/gallery?species="+ species + "&family=All&subfamily=All&type=All&view=All&stage=All&sex=All&region=All"

        page = requests.get(url)
        soup = BeautifulSoup(page.content, 'html.parser')
        content = soup.find_all('img')
        if(len(content)>3):
            print(content[1]['src'])

            #Downloads file.
            fileName = species + ".jpg"
            fileName = fileName.replace('?','')

            r = requests.get(content[1]['src'],stream = True)
            with open(fileName,'wb') as f:
                shutil.copyfileobj(r.raw, f)
                print("File sucessfully Downloaded")
        else:
            print("Image could not be found")

    except:
        print("Image could not be found")