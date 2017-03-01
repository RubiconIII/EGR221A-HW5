// This is a starter file for QuestionsGame.
//
// You should delete this comment and replace it with your class
// header comment.

import java.io.PrintStream;
import java.util.Scanner;

public class QuestionsGame {
    private QuestionNode root;

    // pre: Takes in object
    // post: Initializes a new QuestionsGame using passed in object.
    public QuestionsGame(String object) {
        this.root = new QuestionNode(object);
    }

    // Initializes a new QuestionsGame from a given Scanner
    // pre: Takes in a non-null Scanner
    // post: Initializes a new QuestionsGame with all objects and questions from scanner
    public QuestionsGame(Scanner input) {
        this.root = this.QuestionsGameHelper(input);
    }

    // pre: Takes in the input from QuestionsGame
    // constructor, assumes the input is in standard form.
    // post: Initializes a new QuestionsGame creating a tree. Returns
    // QuestionNode current
    private QuestionNode QuestionsGameHelper(Scanner input) {
        String type = input.nextLine();
        QuestionNode current = new QuestionNode(input.nextLine());

        if (type.equals("Q:")) {
            current.left = this.QuestionsGameHelper(input);
            current.right = this.QuestionsGameHelper(input);
        }
        return current;
    }

    // pre: Takes in PrintStream output
    // post: Throws an IllegalArgument Exception if PrintStream is null.
    // else saves the questions and answers to an output file.
    public void saveQuestions(PrintStream output) {
        if (output == null) {
            throw new IllegalArgumentException("Non valid PrintStream");
        }
        QuestionNode current = this.root;
        this.saveQuestions(output, current);
    }

    // pre: Takes in a non-null PrintStream output and a QuestionNode current
    // post: Saves the current questions tree to an output file shown
    // by the given PrintStream.
    private void saveQuestions(PrintStream output, QuestionNode current) {
        if (current.left == null && current.right == null) {
            output.println("A:");
            output.println(current.data);
        }
        else {
            output.println("Q:");
            output.println(current.data);
            if (current.left != null) {
                this.saveQuestions(output, current.left);
            }
            if (current.right != null) {
                this.saveQuestions(output, current.right);
            }
        }
    }

    // post: Plays game with current questions
    public void play() {
        QuestionNode current = this.root;
        Scanner console = new Scanner(System.in);
        this.root = this.play(current, console);
    }

    // pre: Takes in a QuestionNode current
    // post: Plays game with the current
    // questions, asks yes or no questions until there is a guess.
    // Returns a QuestionNode current. Modifies the tree if guesses wrong
    // to include the new object and question.
    private QuestionNode play(QuestionNode current, Scanner console) {
        if (current.left == null && current.right == null) {
            System.out.println("I guess that your object is "
                    + current.data + "!");
            System.out.print("Am I right? (y/n)? ");
            if (console.nextLine().trim().toLowerCase().startsWith("y")) {
                System.out.println("Awesome! I win!");
            }
            else {
                // x = change(x)
                current = this.machnLearn(current, console);
            }
        }
        else {
            System.out.print(current.data + " (y/n)? ");
            if (console.nextLine().trim().toLowerCase().startsWith("y")) {
                current.left = play(current.left, console);
            } else {
                current.right = play(current.right, console);
            }
        }
        return current;
    }


    // pre: Takes in QuestionNode current
    // post: Returns a QuestionNode newQuestion that has the new question
    // as its data, the incorrect guess as one of its leaves, and the
    // new object as other leaf.
    private QuestionNode machnLearn(QuestionNode current, Scanner console) {
        System.out.println("Boo! I Lose.  Please help me get better!");
        System.out.print("What is your object? ");
        String o = console.nextLine();
        System.out.println("Please give me a yes/no question that "
                + "distinguishes between " + o + " and " + current.data + ".");
        System.out.print("Q: ");

        QuestionNode ques = new QuestionNode(console.nextLine());
        System.out.print("Is the answer \"yes\" for " + o + "? (y/n)? ");
        if (console.nextLine().trim().toLowerCase().startsWith("y")) {
            ques.left = new QuestionNode(o);
            ques.right = current;
        } else {
            ques.left = current;
            ques.right = new QuestionNode(o);
        }
        return ques;
    }

    // Each QuestionNode represents a single node in the tree for a
    // QuestionsGame
    private static class QuestionNode {
        public final String data;
        public QuestionNode left;
        public QuestionNode right;

        // Initializes a QuestionNode when given String data
        public QuestionNode(String data) {
            this(data, null, null);
        }

        // Given a String data, QuestionNode left, and QuestionNode right,
        // initializes a new QuestionNode
        public QuestionNode(
                String data, QuestionNode left, QuestionNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
}