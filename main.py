# 3 fields post to host_fav

host_fav = 'http://www.gnoosic.com/faves.php'
host_result = 'http://www.gnoosic.com/artist/'

headers = {
    'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36',
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
    'Accept-Languages': 'en-US,en;q=0.9,fr-FR;q=0.8,fr;q=0.7',
    'Cookie': '_ga=GA1.2.157438752.1583397522; _gid=GA1.2.273672382.1583397522; s=2350432; _gat=1'
}

hidden_fields = ['skip'] # hidden on host
fields = ['Fave01', 'Fave02', 'Fave03']

import requests
import re

r = requests.post(host_fav, headers=headers, data={hidden_fields[0]: '1', fields[0]: 'Nicki Minaj', fields[1]: 'The Beatles', fields[2]: 'Led Zeppelin'})

r.url.replace(host_result, '').replace('+', ' ')

hidden_fields = ['SuppID'] # hidden on result
fields = ['RateP01', 'RateN01', 'Rate00']

re.search('<input type=hidden name="SuppID" value="(.*)">', r.text).group(1)

r = requests.post(host_result, headers=headers, data={hidden_fields[0]: re.search('<input type=hidden name="SuppID" value="(.*)">', r.text).group(1), fields[0]: 'I like it'})

with open('result.html', mode='w') as file:
    file.write(r.text)
