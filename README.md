# mini-retrieval

A mini retrieval system which allows to answer queries within locally specified dataset.

# Scope

The system should take care of the indexing and querying with a small command line interface.

- It should be possible to specify a custom set of stopwords to minimize the overhead of meaningless words.
- It should be possible to implement a custom stemming algorithm by using a certain interface.
- It should be possible to implement various adapters for specific filetypes such as txt, pdf, pptx, etc.

# Goals

- The user should be able to ask queries on limited dataset. The limited dataset should ensure that only categorically relevant information is indexed. An example would be a set of pdfs which contain lecture slides.

# Non-Goals

- There are tools available such as [terrier](http://terrier.org/) which would already provide functionality such as required above. This project should not make use of this or any similar tools as it only serves as a PoC for information retrieval systems.

