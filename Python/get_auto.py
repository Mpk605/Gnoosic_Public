import requests
import re

host_fav = 'http://www.gnoosic.com/faves.php'
host_result = 'http://www.gnoosic.com/artist/'
db_host = 'http://cdn.gnoosic.com/typeahead?'

headers = {
    'Accept': '*/*',
    'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.2 Safari/605.1.15'
}

r = requests.get(db_host, params={'query': 'The'})

r.text

with open('result.html', mode='w') as file:
    file.write(r.text)
