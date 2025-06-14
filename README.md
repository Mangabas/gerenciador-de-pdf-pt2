# Alunos
- **Lucas Eduardo Mangabeira de Melo**
- **José Wenderson da Cunha Silva**
- **Erick Felipe Santos da Silva**
- **Carlos Henrique da Silva Guimaraes**

# Sistema de Biblioteca Digital

Um sistema de gerenciamento de biblioteca digital desenvolvido em Java que permite organizar e gerenciar documentos PDF (livros, slides e notas de aula) em bibliotecas e coleções personalizadas.

## 📋 Funcionalidades

### Gerenciamento de Bibliotecas
- **Criar biblioteca**: Criação de novas bibliotecas com estrutura de diretórios organizada
- **Trocar biblioteca**: Alternância entre diferentes bibliotecas existentes
- **Listar bibliotecas**: Visualização de todas as bibliotecas disponíveis

### Gerenciamento de Documentos PDF
- **Adicionar livros**: Inclusão de livros com metadados completos (autor, título, subtítulo, área, ano)
- **Adicionar slides**: Adição de apresentações com informações da disciplina
- **Adicionar notas de aula**: Inclusão de notas com subtítulo e disciplina
- **Listar PDFs**: Visualização de todos os documentos na biblioteca atual

### Sistema de Coleções
- **Criar coleções**: Agrupamento de documentos por tipo e critérios específicos
- **Gerenciar coleções**: Adicionar e remover documentos de coleções existentes
- **Trocar coleção**: Alternância entre diferentes coleções
- **Filtrar por tipo**: Visualização de documentos por categoria (livro, slide, notas)
- **Filtrar por autor**: Busca de documentos por autor específico

### Exportação e Compartilhamento
- **Exportar para BibTeX**: Geração de arquivos de referência bibliográfica
- **Criar arquivo ZIP**: Compactação de coleções para compartilhamento

## 🏗️ Estrutura do Projeto

```
├── Main.java                          # Classe principal com interface de comandos
├── biblioteca/
│   └── BibliotecaService.java         # Serviços de gerenciamento de biblioteca
├── exception/
│   ├── BibliotecaNaoEncontradaException.java
│   └── ColecaoVaziaException.java     # Exceções customizadas
├── models/
│   ├── ArquivoPDF.java               # Classe abstrata base para documentos
│   ├── Livro.java                    # Modelo para livros
│   ├── Slide.java                    # Modelo para slides
│   ├── NotaDeAula.java               # Modelo para notas de aula
│   └── FilterDePDF.java              # Utilitário de filtragem genérica
└── utils/
    ├── Biblioteca.java               # Interface para bibliotecas
    ├── CollectionMethods.java        # Métodos para gerenciamento de coleções
    ├── LibMethods.java               # Métodos para gerenciamento de bibliotecas
    └── SimpleSerializationLib.java   # Utilitário de serialização
```

## 🚀 Como Usar

### Observação importante
-NÃO UTILIZAR CARACTERES ESPECIAIS. Devido ao estilo adotador de serialização, podem acontecer alguns erros ao utilizar caracteres especiais

### Compilação
```bash
javac -cp . Main.java biblioteca/*.java models/*.java utils/*.java exception/*.java
```

### Comandos Principais

#### Gerenciamento de Biblioteca
```bash
# Criar nova biblioteca
java Main create-lib <nome_biblioteca>

# Listar bibliotecas disponíveis
java Main list-libs

# Trocar biblioteca atual
java Main change-lib
```

#### Adição de Documentos
```bash
# Adicionar livro
java Main add-book <autor> <titulo> <subtitulo> <area> <ano> <caminho_pdf>

# Adicionar slide
java Main add-slide <titulo> <disciplina> <autor> <caminho_pdf>

# Adicionar notas de aula
java Main add-notes <titulo> <subtitulo> <disciplina> <autor> <caminho_pdf>

# Listar todos os PDFs
java Main list-pdf
```

#### Gerenciamento de Coleções
```bash
# Criar coleção
java Main create-collection <tipo> <limite> <nome_colecao>

# Ler coleção atual
java Main read-collection

# Adicionar documento à coleção
java Main add-to-collection <titulo> <nome_colecao> <tipo>

# Remover documento da coleção
java Main delete-to-collection <titulo> <nome_colecao>

# Trocar coleção
java Main change-collection
```

#### Filtros e Buscas
```bash
# Listar por tipo
java Main collection-by-type <tipo>

# Listar por autor
java Main collection-by-author <autor>
```

#### Exportação
```bash
# Exportar para BibTeX
java Main to-bibtex <caminho_destino> <nome_arquivo>

# Criar arquivo ZIP
java Main to-zip <caminho_destino> <nome_colecao>
```

### Exemplo de Uso Completo

```bash
# 1. Compilar o projeto
javac -cp . Main.java biblioteca/*.java models/*.java utils/*.java exception/*.java

# 2. Criar uma biblioteca
java Main create-lib MinhaBlioteca

# 3. Adicionar documentos
java Main add-book "João Silva" "Programação Java" "Conceitos Avançados" "Computação" 2023 "/caminho/para/livro.pdf"
java Main add-slide "Introdução à POO" "Programação" "Prof. Maria" "/caminho/para/slides.pdf"

# 4. Listar documentos
java Main list-pdf

# 5. Criar uma coleção
java Main create-collection book 10 ColecaoJava

# 6. Exportar para BibTeX
java Main to-bibtex "/destino/" "referencias"
```

## 📁 Estrutura de Armazenamento

O sistema cria automaticamente uma estrutura de diretórios organizada:

```
bin/
├── <nome_biblioteca>/
│   ├── Book/           # Livros
│   ├── Slide/          # Apresentações
│   └── Notes/          # Notas de aula
├── Collections/        # Coleções personalizadas
└── *.ser              # Arquivos de serialização
```

## ⚙️ Recursos Técnicos

- **Serialização**: Persistência de dados usando serialização Java nativa
- **Cópia de Arquivos**: Cópia automática de PDFs para estrutura organizada
- **Filtragem Genérica**: Sistema de filtros type-safe usando generics
- **Tratamento de Exceções**: Exceções customizadas para melhor controle de erros
- **Exportação BibTeX**: Geração automática de referências bibliográficas
- **Compressão ZIP**: Criação de arquivos compactados para compartilhamento

## 🔧 Requisitos

- **Java 8+**: Para suporte a streams e recursos modernos
- **Sistema de arquivos**: Permissões de leitura/escrita no diretório de trabalho
- **Documentos PDF**: Arquivos devem estar acessíveis nos caminhos especificados

## 🎯 Casos de Uso

- **Estudantes**: Organização de material de estudo por disciplina
- **Professores**: Gerenciamento de slides e material didático
- **Pesquisadores**: Organização de referências bibliográficas
- **Bibliotecas Digitais**: Catalogação e organização de acervo PDF

## 🐛 Tratamento de Erros

O sistema inclui tratamento robusto para:
- Bibliotecas não encontradas
- Coleções vazias
- Arquivos inexistentes
- Problemas de serialização
- Parâmetros insuficientes nos comandos

## 📝 Notas de Desenvolvimento

- Todos os documentos são copiados para a estrutura da biblioteca
- Os metadados são preservados através de serialização
- O sistema suporta múltiplas bibliotecas simultâneas
- Coleções podem conter documentos de diferentes tipos
- Filtros permitem busca eficiente por critérios específicos
