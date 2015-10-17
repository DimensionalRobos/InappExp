import sys
import  requests

from bs4 import BeautifulSoup   

r =         requests.get("http://www.urbandictionary.com/define.php?term={}".format(sys.argv[1]))

soup = BeautifulSoup(r.content)
        
for definition in soup.find_all("div",attrs={"class":"meaning"}):
    print(sys.argv[1],"==",definition.text)