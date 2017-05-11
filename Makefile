SRCDIR 	 := src
CLASSDIR := class

SRC      := $(wildcard $(SRCDIR)/*.java)
CLASS    := $(patsubst $(SRCDIR)/%.java, $(CLASSDIR)/%.class, $(SRC))

JC       := javac -d

$(CLASS): $(SRC) | $(CLASSDIR)
	$(JC) $(CLASSDIR) $(SRC)

.PHONY: init
init: $(SRCDIR) $(CLASSDIR)

$(SRCDIR) $(CLASSDIR):
	mkdir -p $@
