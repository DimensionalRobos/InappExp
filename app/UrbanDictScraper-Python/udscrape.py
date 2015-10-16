import  requests

from bs4 import BeautifulSoup   

def udscrape(word):
    r =         requests.get("http://www.urbandictionary.com/define.php?term={}".format(word))

    soup = BeautifulSoup(r.content)

    print(soup.find("div",attrs={"class":"meaning"}).text)