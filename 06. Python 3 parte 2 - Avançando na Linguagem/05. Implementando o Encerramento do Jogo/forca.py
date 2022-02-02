def jogar():

    print("*********************************")
    print("***Bem vindo ao jogo da Forca!***")
    print("*********************************")

    palavra_secreta = "python".upper()
    letras_acertadas = []

    letras_acertadas = ["_" for letra in palavra_secreta]

    erros = 0
    print(len(palavra_secreta))
    print(letras_acertadas)

    while (True):

        chute = input("Qual letra? ")
        chute = chute.strip().upper()

        if (chute in palavra_secreta):
            index = 0
            for letra in palavra_secreta:
                if (chute == letra):
                    letras_acertadas[index] = letra
                index += 1
        else:
            erros += 1
            print("Ops, você errou! Faltam {} tentativas.".format(6-erros))

        if (erros == 6):
            break
        if ("_" not in letras_acertadas):
            break
        print(letras_acertadas)
        
    if ("_" not in letras_acertadas):
        print("Você ganhou!")
    else:
        print("Você perdeu!")

    print("Fim do jogo")

if(__name__ == "__main__"):
    jogar()
