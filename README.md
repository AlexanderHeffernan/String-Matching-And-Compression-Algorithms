## Boyer-Moore, Huffman Coding, KMP, and Lempel-Ziv Algorithms Implementations

This repository contains implementations of the Boyer-Moore algorithm, Huffman Coding, the Knuth-Morris-Pratt (KMP) algorithm, and the Lempel-Ziv algorithm. These algorithms were developed as part of an assignment to demonstrate proficiency in string matching and data compression techniques.

### Algorithms Implemented

#### BoyerMoore.java
The Boyer-Moore algorithm is a highly efficient string matching algorithm used to find occurrences of a pattern within a text. By utilizing preprocessing steps that create skip tables, Boyer-Moore significantly reduces the number of comparisons required, making it particularly effective for large texts. This implementation of the Boyer-Moore algorithm demonstrates expertise in advanced string matching techniques and their optimization.

#### HuffmanCoding.java
Huffman Coding is a fundamental algorithm in data compression, widely used in various compression formats such as ZIP and JPEG. The algorithm creates an optimal prefix code based on the frequencies of characters in the input data, resulting in a compressed representation. This implementation of Huffman Coding showcases proficiency in creating and using binary trees for efficient data encoding and decoding.

#### KMP.java
The Knuth-Morris-Pratt (KMP) algorithm is another powerful string matching algorithm that improves upon the naive approach by preprocessing the pattern to determine the longest prefix which is also a suffix. This allows the algorithm to skip unnecessary comparisons, making it efficient for searching in large texts. This implementation of the KMP algorithm highlights the ability to optimize search operations in text processing.

#### LempelZiv.java
The Lempel-Ziv algorithm is a lossless data compression algorithm that forms the basis of several universal compression formats, including LZ77 and LZ78. It works by finding repeating patterns in the input data and replacing them with shorter representations. This implementation of the Lempel-Ziv algorithm demonstrates competence in designing algorithms for effective data compression and decompression.

### Usage
1. Compile the Java files using a Java compiler.
2. Integrate the compiled Java programs into your own projects to utilize the algorithms.
3. Follow the instructions provided within each program for specific usage guidelines for each function.

### Acknowledgements
These implementations were developed as part of a project for COMP261 at Victoria University of Wellington.
