# Shakespeare

This is a program to generate new sonnets using the vocabulary used by Shakespeare.
In doing so it uses a Markow chain model of dimension 5.
This means that the last 5 words will be considered to chose the next word out of the vocabulary.

The program analyzes the 154 sonnets by Shakespeare and formulates relations between the words.

In a second step the user is invited to set a start word and a noise factor to generate a new sonnet.

Noise factor

The higher the noise factor the less deterministic the output will be.
The lower the noise factor the the higher is the probability to run into a word circle.

Start the application via 'java shakespeare.Main' from the superordinated folder from the command line.
