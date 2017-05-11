SRCDIR 	 := src
CLASSDIR := class
PWD      := $(shell pwd)

SRC      := $(wildcard $(SRCDIR)/*.java)
CLASS    := $(patsubst $(SRCDIR)/%.java, $(CLASSDIR)/%.class, $(SRC))

CLASSD   := $(wildcard $(PWD)/*.class)
SRCD     := $(wildcard $(PWD)/*.java)

JC       := javac -d
MV		 := mv

.PHONY: compile
compile: $(CLASS)

$(CLASS): $(SRC)
	$(JC) $(CLASSDIR) $<

.PHONY: init
init: $(SRCDIR) $(CLASSDIR)

$(SRCDIR) $(CLASSDIR):
	mkdir -p $@

.PHONY: organize
organize: init
		-$(MV) $(CLASSD) $(CLASSDIR)
		-$(MV) $(SRCD) $(SRCDIR)
