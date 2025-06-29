
# [Tools Cars](#tools-cars)  

O aplicativo Tools Cars visa oferecer uma solução prática e acessível para consultar informações atualizadas sobre veículos automotores carros no Brasil. Utilizando dados da API pública da Tabela FIPE, o app permitirá que usuários pesquisem por marcas, modelos, versões e visualizem os preços médios com base na FIPE. Além disso, será possível salvar veículos como favoritos para consultas futuras.

## [Sumário](#sumario)

- [Tools Carts](#tools-cars)
  - [Sumário](#sumario) 
  - [API](#api)
    - [1. Endpoint Marca](#endpoint-marca)
	- [2. Endpoint Modelos](#endpoint-modelos)
	- [3. Endpoint Anos](#endpoint-anos)
	- [4. Endpoint Master Detail](#endpoint-master)
  - [Telas](#telas)
    - [1. Tela Marcas/Inicial](#tela-marcas)
	- [2. Tela Modelos](#tela-modelos)
	- [3. Tela Anos](#tela-anos)
	- [4. Tela Master Detail](#tela-master)
	- [5. Tela Favoritos](#tela-master)
  - [Estrutura do Projeto](#estrutura) 
  - [Vídeo](#video) 
  - [Autor](#autor) 


## [API](#api)

As APIs utilizadas para esse trabalho são do do site Parallelum, sem necessidade de autenticação, todos com request via GET, alguns passando parâmetros por GET: 

#### [1. Endpoint Marca](#endpoint-marca)

Nesse caso basta acessar a API, para pegar o código da marca.

GET [https://parallelum.com.br/fipe/api/v1/carros/marcas](https://parallelum.com.br/fipe/api/v1/carros/marcas)

Response: 
```bash
 [
	{
		"codigo": "1",
		"nome": "Acura"
	},
	{
		"codigo": "2",
		"nome": "Agrale"
	},
	{
		"codigo": "3",
		"nome": "Alfa Romeo"
	} 
    ...
]
```    

#### [2. Endpoint Modelos](#endpoint-modelos)

Passando o código da marca, com esse endpoint retorna os modelos da marca escolhida.

GET [https://parallelum.com.br/fipe/api/v1/carros/marcas/48/modelos](https://parallelum.com.br/fipe/api/v1/carros/marcas/48/modelos)

Response: 
```bash
{
	"modelos": [
		{
			"codigo": 1986,
			"nome": "19 16S/ RT 16V"
		},
		{
			"codigo": 1987,
			"nome": "19 RN"
		},
		{
			"codigo": 1988,
			"nome": "19 RT 1.8/ 1.8i"
		}
		...
}
``` 

#### [3. Endpoint Anos](#endpoint-anos)

Passando o código da marca e o código do modelo, com esse endpoint retorna os anos do modelo escolhido.

GET [https://parallelum.com.br/fipe/api/v1/carros/marcas/48/modelos/4506/anos](https://parallelum.com.br/fipe/api/v1/carros/marcas/48/modelos/4506/anos)

Response: 
```bash
[
	{
		"codigo": "2014-1",
		"nome": "2014 Gasolina"
	},
	{
		"codigo": "2013-1",
		"nome": "2013 Gasolina"
	},
	{
		"codigo": "2012-1",
		"nome": "2012 Gasolina"
	}
	...
]
``` 

#### [4. Endpoint Master Detail](#endpoint-master)

Com todos as informações anterior, basta concatenar nesse endpoint que é retornado todas as informações, inclusíve valor da FIPE do veículo escolhido.

GET [https://parallelum.com.br/fipe/api/v1/carros/marcas/48/modelos/4506/anos](https://parallelum.com.br/fipe/api/v1/carros/marcas/48/modelos/4506/anos)

Response: 
```bash
{
	"TipoVeiculo": 1,
	"Valor": "R$ 32.122,00",
	"Marca": "Renault",
	"Modelo": "SANDERO Expression Hi-Flex 1.0 16V 5p",
	"AnoModelo": 2014,
	"Combustivel": "Gasolina",
	"CodigoFipe": "025143-7",
	"MesReferencia": "junho de 2025",
	"SiglaCombustivel": "G"
}
``` 


## Telas(#telas)

#### [1. Tela Marcas/Inicial](#tela-marcas)

Na tela inicial do aplicativo, há uma barra de navegação inferior com duas opções: Início e Favoritos. A navegação entre as telas é feita utilizando Fragments, proporcionando uma experiência fluida ao usuário.

Na parte superior, há uma barra de pesquisa com ícone de lupa, onde o usuário pode selecionar a marca do carro desejada e clicar para continuar.

![1](/img/1.png)


#### [2. Tela Modelos](#tela-modelos)

Na tela dos modelos do aplicativo, o usuário deve escolher o modelo.

![2](/img/2.png)

#### [3. Tela Anos](#tela-anos)

Na tela dos anos do aplicativo, o usuário escolher o ano do veículo.

![3](/img/3.png)

#### [4. Tela Master Detail](#tela-master)

Na tela dos master detail do aplicativo, Mostra os detalhes do veículo escolhido dando a possibilidade de favoritar esse veículo para fácil acesso a ele.

![4](/img/4.png)

#### [5. Tela Favoritos](#tela-favoritos)

Na tela de favoritos, além de listar todos os veículo favoritados, ao clicar o usuário é direcionado para a tela Master Detail do veículo e também ao arrastar para o lado o veículo é desfavoritado. 

![5](/img/5.png)

## [Estrutura do Projeto](#aestrutura)

A seguir a estrutura dos arquivos e do projeto inteiro.

```txt
/
├── app/
│   ├── java/
│   │   └── com.example.tools_cars/
│   │       ├── adapter/
│   │       ├── dao/
│   │       ├── entity/
│   │       ├── fragment/
│   │       ├── retrofit/
│   │       ├── room/
│   │       └── MainActivity.java
│   └── res/
│       └── layout/
│           ├── activity_main.xml
│           ├── fragment_ano.xml
│           ├── fragment_carro.xml
│           ├── fragment_favoritos.xml
│           ├── fragment_marcas.xml
│           ├── fragment_modelos.xml
│           ├── item.xml
│           └── item_favoritos.xml
├── build.gradle
└── README.md
```

## [API](#api)

### app/java/com.example.tools_cars/

- **adapter/**  
  Contém classes adaptadoras que ligam dados às views (RecyclerView).

- **dao/**  
  Interfaces DAO para acesso ao banco local via Room.

- **entity/**  
  Entidades que representam tabelas do banco de dados local.

- **fragment/**  
  Fragments para as telas do app: marcas, modelos, anos, detalhes, favoritos.

- **retrofit/**  
  Interfaces e configurações para consumir API FIPE via Retrofit.

- **room/**  
  Classe AppDatabase e configuração do banco Room.

- **MainActivity.java**  
  Activity principal que gerencia navegação e inicialização do app.

### app/res/layout/

- **activity_main.xml**  
  Layout principal da Activity com container de fragments e navegação.

- **fragment_ano.xml**  
  Layout para escolha do ano/versão do carro.

- **fragment_carro.xml**  
  Layout com detalhes do carro consultado (preço FIPE, etc).

- **fragment_favoritos.xml**  
  Layout da lista de carros favoritos.

- **fragment_marcas.xml**  
  Layout da lista de marcas.

- **fragment_modelos.xml**  
  Layout da lista de modelos.

- **item.xml**  
  Layout individual para itens de lista (marcas, modelos, anos).

- **item_favoritos.xml**  
  Layout individual para itens de favoritos.

---

## Vídeo 

[Vídeo](https://choosealicense.com/licenses/mit/)


## [Autor](#autor)

| [<img src="https://github.com/guglieelmor.png?size=115" width=115><br><sub>@guglieelmor</sub>](https://github.com/guglieelmor) 
| :---: |

