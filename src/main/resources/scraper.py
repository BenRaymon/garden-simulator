import json
import requests
from bs4 import BeautifulSoup
import time
import csv


def addToFile(commonName,scientificName,family,color,sizeLower,sizeUpper,RadiusLow,RadiusHigh,sunLevel,moisture,soilType,photoLink):

    tempDict = {
        'commonName' : commonName,
        'scientificName' : scientificName,
        'family' : family,
        'color' : color,
        'sizeLower' : sizeLower,
        'sizeUpper' : sizeUpper,
        'spreadRadiusLower' : RadiusLow,
        'spreadRadiusUpper' : RadiusHigh,
        'sunLevel' : sunLevel,
        'moisutre' : moisture,
        'soilType' : soilType,
        'cost' : 'N/A',
        'lepsSupported' : 'N/A',
        'type' : 'N/A',
        'photoLink' : photoLink
    }
    '''
    f = open("plantInfo.txt", "a")
    y = json.dumps(tempDict)
    print(y) 
    f.write(y+'\n')
    f.close()
    '''
    return tempDict

    csv_columns = ['commonName','scientificName','family','color','sizeLower','sizeUpper','spreadRadiusLower','spreadRadiusUpper','sunLevel','moisutre','soilType','cost','lepsSupported','type']
    #csv_columns = []

    with open("plantInfo.csv", 'a') as csvfile:
        writer = csv.DictWriter(csvfile, fieldnames=csv_columns)
        writer.writeheader()
        writer.writerow(tempDict)


if __name__ == "__main__":
    #overwrites old file to be blank
    #f = f = open("plantInfo.txt", "w")
    #f.write("")
    #f.close()
    arr = []

    for r in range(1,36):
        print("On page: "+str(r))
        #addToFile()
        URL = 'http://www.nativeplantcenter.net/plants/page/' + str(r)
        page = requests.get(URL)
        #looking for class = "content_image"

        soup = BeautifulSoup(page.content, 'html.parser')
        plantList = soup.find_all('div',class_ = 'content_image')

        for p in plantList:
            common_name = p.find('small').getText()
            photo_link = p.find('img')['src']
            scientificName = ""

            family = ""
            color = ""
            sizeLower = ""
            sizeUpper = ""
            spreadRadiusLower = ""
            spreadRadiusUpper = ""
            sunLevel = ""
            moisture = ""
            soilType = ""



            more_info_link = p.find('a')['href']
        
        
            URL2 = more_info_link
            page2 = requests.get(URL2)
            soup2 = BeautifulSoup(page2.content, 'html.parser')
            info = soup2.find('div',class_ = 'col-md-5')
            
            #Gets scientific name
            scientificName = soup2.find('h1', class_ = "title-left").getText()
            
            pBlocks = info.find_all('p')
            for pB in pBlocks:
                #print(pB.getText())
                x = pB.getText().split(': ')
                #print(x)
                if(x[0] == 'Family'):
                    family = x[1]
                if(x[0] == 'Flower Color'):
                    color = x[1]
                if(x[0] == 'Sun Exposure'):
                    sunLevel = x[1]

                if(x[0] == 'Soil Moisture'):
                    moisture = x[1]
                if(x[0] == 'Soil Texture'):
                    soilType = x[1]
                if(x[0] == 'Height'): #For size
                    print(x)
                    y = x[1].split(' - ')
                    sizeLower = y[0]
                    sizeUpper = y[1]
                    try:
                        y = x[2].split(' - ')
                        spreadRadiusLower = y[0]
                        spreadRadiusUpper = y[1]
                        spreadRadiusUpper = spreadRadiusUpper[:-2]
                        sizeUpper = sizeUpper[:-8]

                    except:
                        print("No spread data")
                        spreadRadiusLower = "N/A"
                        spreadRadiusUpper = "N/A"
            arr.append(addToFile(common_name,scientificName,family,color,sizeLower,sizeUpper,spreadRadiusLower,spreadRadiusUpper,sunLevel,moisture,soilType,photo_link))
            print("Plant done")
            #time.sleep(0.5)
    print("Writing plants to csv file")
    csv_columns = ['commonName','scientificName','family','color','sizeLower','sizeUpper','spreadRadiusLower','spreadRadiusUpper','sunLevel','moisutre','soilType','cost','lepsSupported','type',"photoLink"]
    with open("plantInfo.csv", 'w') as csvfile:
        writer = csv.DictWriter(csvfile, fieldnames=csv_columns)
        writer.writeheader()
        for data in arr:
            writer.writerow(data)
    print("All plants scraped")

