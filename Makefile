JFLAGS = -g -Xlint:none -cp
JC = javac
JR = java
F = find
RM = rm -rf
CURRENT_TEST = test6b

build:
	javac BellmanFord.java
	javac Dijkstra.java
	javac DijkstraWithoutPQ.java

run: BellmanFord.class Dijkstra.class DijkstraWithoutPQ.class
	java BellmanFord in/$(CURRENT_TEST).in > out/$(CURRENT_TEST)_bf.out
	java Dijkstra in/$(CURRENT_TEST).in > out/$(CURRENT_TEST)_dij.out
	java DijkstraWithoutPQ in/$(CURRENT_TEST).in > out/$(CURRENT_TEST)_dij_nopq.out

clean:
	@$(RM) *.class sources.txt  .class
	@$(F) . -type f -path "./*/*" -name "*.class" -exec $(RM) -f {} \; &> /dev/null
