### Encroach

Encroach is a simple territory-conquering game that was originally inspired by a Flash game called [Filler][filler] on the Russian mail provider, Mail.ru. The game is fairly simple, but I found it to be surprisingly fun. It has been a good exercise in some basic algorithms and object-oriented programming.

### Building

The game is implemented in several languages (currently Java and C++), and each can be built from the top-level directory. To build a given implementation, simply type `make` followed by the implementation's name, in all lower-case characters. Alternatively, run `make all` to build all implementations. For example:

    make java
    make c++

or:

    make all

### Running

This game doesn't yet come in a neat OS-targeted package, so running each implementation will be different.

#### Java

The Makefile creates a .jar file, which can be executed from the command line like so:

    java -jar encroach.jar

[filler]: http://games.mail.ru/pc/games/filler/

#### C / C++

The Makefile produces an executable binary. Just execute the file:

    ./encroach

If permissions are a problem, allow the binary to be executable first:

    chmod +x encroach
    ./encroach
