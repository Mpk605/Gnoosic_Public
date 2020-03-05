# 3 fields post to host_fav

host_fav = 'http://www.gnoosic.com/faves.php'
host_result = 'http://www.gnoosic.com/artist/'

headers = {
    'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.2 Safari/605.1.15'
}

hidden_fields = ['skip'] # hidden on host
fields = ['Fave01', 'Fave02', 'Fave03']

import requests
import re

r = requests.post(host_fav, headers=headers, data={hidden_fields[0]: '1', fields[0]: 'Nicki Minaj', fields[1]: 'The Beatles', fields[2]: 'Led Zeppelin'})

r.url.replace(host_result, '').replace('+', ' ')

hidden_fields = ['SuppID'] # hidden on result
fields = ['RateP01', 'RateN01', 'Rate00']

r = requests.post(host_fav, headers=headers, data={hidden_fields[0]: re.search('<input type=hidden name="SuppID" value="(.*)">', r.text).group(1), fields[0]: 'I don\'t think the value matters, am I right ?'})

with open('result.html', mode='w') as file:
    file.write(r.text)
