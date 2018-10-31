from bs4 import BeautifulSoup
import sys
experimentfile=str(raw_input())
f=open(experimentfile,'r')
srchtml=f.read()
print srchtml
f.close()
soup = BeautifulSoup(srchtml, 'html.parser')
att = ''+"list-of-experiments"
tagger = soup.findAll('ul', attrs={'id':att})
tag=tagger[0].text

print tag
