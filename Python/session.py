host_fav = 'http://www.gnoosic.com/faves.php'
host_result = 'http://www.gnoosic.com/artist/'

headers = {
    'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.2 Safari/605.1.15',
}

hidden_fields = ['skip'] # hidden on host
fields = ['Fave01', 'Fave02', 'Fave03']

import requests
import re

r = requests.Session()
result = r.post(host_fav, headers=headers, json={hidden_fields[0]: '1', fields[0]: 'Nicki Minaj', fields[1]: 'The Beatles', fields[2]: 'Led Zeppelin'})
r.cookies
