
# import requests as r
#
# result = r.get('http://gnoosic.com/faves.php', headers={}, data={'skip': '1', 'Fave01': '', 'Fave02': '', 'Fave03': ''})
#
# for i in result.headers:
#     print(i)
#
# with open('result.html', mode='wb') as file:
#     file.write(result.content)

import requests as r

result = r.get('http://cdn.gnoosic.com/typeahead?query=gdg')

with open('result.html', mode='wb') as file:
    file.write(result.content)
