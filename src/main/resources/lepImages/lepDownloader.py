import csv
import requests
import shutil
import time

with open('../LepImages.csv',newline='') as csvfile:
    read = csv.reader(csvfile, delimiter=',')
    count = 0
    for row in read:
        time.sleep(0.3)
        if(len(row) > 0):
            if(row[1] != 'None' and row[1] != 'Lep Url' and row[1] != ''):
                print(str(count) +" " + row[1])
                count = count + 1
                fileName = row[0] + ".jpg"
                fileName = fileName.replace('?','')
                #print(row[1]+"-----------------------------")
                r = requests.get(row[1],stream = True)

                if(r.status_code == 200):
                    r.raw.decode_content = True

                    with open(fileName,'wb') as f:
                        shutil.copyfileobj(r.raw, f)
                    print("File sucessfully Downloaded")

                else:
                    print("Couldn't retreive image")

