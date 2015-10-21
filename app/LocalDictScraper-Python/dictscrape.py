import requests
import sys

from bs4 import BeautifulSoup   

def dictscrape(word):
    #Define the path of your UrbanDictionary based Custom Dictionary.
    soup = BeautifulSoup(open("C:\\Users\\user\\Documents\\GitHub\\InappExp\\docs\\Scrape-InappExp.html").read())
    for definition in soup.find_all("div",attrs={"class":word}):
        print(word,"means this:",definition.text)