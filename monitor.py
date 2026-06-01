import urllib.request
import time


API_URL = "http://app:3000/"

print(f"Iniciando monitoramento ativo da API em: {API_URL}")

while True:
    try:
        resposta = urllib.request.urlopen(API_URL)
        codigo_status = resposta.getcode()

        if codigo_status == 200:
            print(f"API Online e respondendo, Status: {codigo_status}")
        else:
            print(f"API respondeu com status inesperado: {codigo_status}")

    except Exception as e:
        print(f"Falha ao conectar com a API. Erro: {e}")

    time.sleep(10)