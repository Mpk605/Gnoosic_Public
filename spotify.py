import requests
import webbrowser

# Auth
# webbrowser.open('https://accounts.spotify.com/authorize?client_id=6ff8b8c459f44a8f9f0bb3e138cb00f2&response_type=code&redirect_uri=http://gnoosic.com')

code = 'AQAiD60DwhQAbZ5BzbQzRFGEoffnvAKJPIU4eftzMcCPIOLM9KP4amjcfTfN6EShEYZRQTwjbEmqwC_84VYbHgoSwh-1KmtKWMcMzIhrTgB0vx4GEYrNfywoyNaTADKB6wXMunRcIj4uy4mz5u_2prjTJQnXNKU1f6-4u6e9rUhPSCwUKQEoBg'

api_search = 'https://api.spotify.com/v1/search'

headers = {
    'Authorization': code
}

result = requests.get(api_search, headers=headers, params={'q': 'Stupeflip', 'type': 'artist'})


with open('result.html', mode='wb') as file:
    file.write(result.content)
