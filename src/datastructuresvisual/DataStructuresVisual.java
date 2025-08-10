/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package datastructuresvisual;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DataStructuresVisual extends JFrame {
    private static final int NODE_SIZE = 30; // This is how big each node will appear, measured in pixels
    private static final int GAP = 50; // The space we leave between nodes, also in pixels

    public DataStructuresVisual() {
        super("Data Structures Visual");
        System.out.println("Welcome to Data Structure Visualizer! \n"
                + "This is a simple program which depicts Arrays, Linked Lists, Queues, Stacks, and Trees \n"
                + "Please select a data structure from the dropdown menu and enter a dataset");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Ask the user which data structure they want to see
        String[] options = { "Array", "Linked List", "Queue", "Stack", "Tree" };
        String selectedOption = (String) JOptionPane.showInputDialog(null, "Select a data structure to visualize:",
                "Data Structure Visualization", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        // Depending on what the user picked, show the right visualization
        switch (selectedOption) {
            case "Array":
                visualizeArray();
                break;
            case "Linked List":
                visualizeLinkedList();
                break;
            case "Queue":
                visualizeQueue();
                break;
            case "Stack":
                visualizeStack();
                break;
            case "Tree":
                visualizeBinaryTree();
                break;
        }

        pack();
        setVisible(true);
    }

    // Show the array visually on the screen
    private void visualizeArray() {
        String input = JOptionPane.showInputDialog(null, "Enter array elements separated by spaces:");
        String[] elements = input.split("\\s+");
        int[] array = Arrays.stream(elements).mapToInt(Integer::parseInt).toArray();
        add(new ArrayVisual(array));
    }

    // Draw the linked list so the user can see it
    private void visualizeLinkedList() {
        String input = JOptionPane.showInputDialog(null, "Enter linked list elements separated by spaces:");
        String[] elements = input.split("\\s+");
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (String element : elements) {
            linkedList.add(Integer.parseInt(element));
        }
        add(new LinkedListVisual(linkedList));
    }

    // Show the queue as a row of boxes
    private void visualizeQueue() {
        String input = JOptionPane.showInputDialog(null, "Enter queue elements separated by spaces:");
        String[] elements = input.split("\\s+");
        Queue<Integer> queue = new LinkedList<>();
        for (String element : elements) {
            queue.add(Integer.parseInt(element));
        }
        add(new QueueVisual(queue));
    }

    // Display the stack as a vertical pile
    private void visualizeStack() {
        String input = JOptionPane.showInputDialog(null, "Enter stack elements separated by spaces:");
        String[] elements = input.split("\\s+");
        Stack<Integer> stack = new Stack<>();
        for (String element : elements) {
            stack.push(Integer.parseInt(element));
        }
        add(new StackVisual(stack));
    }

    // Draw the binary tree based on what the user entered
    private void visualizeBinaryTree() {
        String input = JOptionPane.showInputDialog(null,
                "Enter binary tree elements in level order separated by spaces (use 'null' for empty nodes):");
        String[] elements = input.split("\\s+");
        TreeNode root = createBinaryTreeFromInput(elements);
        add(new BinaryTreeVisual(root));
    }

    // Just a helper to make a small example tree (not used for user input)
    private TreeNode createBinaryTree() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        return root;
    }

    // This is where the program starts running
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DataStructuresVisual::new);
    }

    // This class is just for the nodes in our binary tree
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // This panel actually draws the array on the screen
    static class ArrayVisual extends JPanel {
        private int[] array;

        public ArrayVisual(int[] array) {
            this.array = array;
            // Make sure the panel is big enough so nothing gets cut off
            setPreferredSize(new Dimension(array.length * (NODE_SIZE + GAP) + GAP, NODE_SIZE + 4 * GAP));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < array.length; i++) {
                // Figure out where to put this node
                int nodeX = i * (NODE_SIZE + GAP) + GAP;
                int nodeY = 2 * GAP;

                // Show the index number above the node
                g.drawString(String.valueOf(i), nodeX + NODE_SIZE / 2 - 3, nodeY - 10);

                // Draw the box for the node and put the value inside
                g.drawRect(nodeX, nodeY, NODE_SIZE, NODE_SIZE);
                g.drawString(String.valueOf(array[i]), nodeX + NODE_SIZE / 2 - 3, nodeY + NODE_SIZE / 2 + 5);

                // If this isn't the last node, draw a line to the next one
                if (i < array.length - 1) {
                    int nextNodeX = (i + 1) * (NODE_SIZE + GAP) + GAP;
                    g.drawLine(nodeX + NODE_SIZE, nodeY + NODE_SIZE / 2, nextNodeX, nodeY + NODE_SIZE / 2);
                }
            }
        }
    }

    // This panel draws the linked list with boxes and arrows
    static class LinkedListVisual extends JPanel {
        private LinkedList<Integer> list;

        public LinkedListVisual(LinkedList<Integer> list) {
            this.list = list;
            setPreferredSize(new Dimension(list.size() * (2 * NODE_SIZE + GAP) + GAP, NODE_SIZE + 3 * GAP));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int x = GAP;
            int prevX = 0; // Previous node's x-coordinate
            for (int i = 0; i < list.size(); i++) {
                int value = list.get(i);
                // Draw the first box for the value
                g.drawRect(x, GAP, NODE_SIZE, NODE_SIZE);
                g.drawString(String.valueOf(value), x + NODE_SIZE / 2 - 3, GAP + NODE_SIZE / 2 + 5);
                // Draw the second box (this one is just for the pointer)
                g.drawRect(x + NODE_SIZE, GAP, NODE_SIZE, NODE_SIZE);
                // Put a little dot in the pointer box to show it's a link
                int dotSize = 5;
                int dotX = x + NODE_SIZE + NODE_SIZE / 2 - dotSize / 2;
                int dotY = GAP + NODE_SIZE / 2 - dotSize / 2;
                g.fillOval(dotX, dotY, dotSize, dotSize);

                // If this isn't the first node, draw an arrow from the previous node's pointer
                // to this one
                if (i > 0) {
                    drawArrow(g, prevX + NODE_SIZE / 2, GAP + NODE_SIZE / 2, x, GAP + NODE_SIZE / 2);
                }

                prevX = x + NODE_SIZE;
                x += 2 * NODE_SIZE + GAP;
            }
            g.drawString("Head", GAP, GAP - 10);
        }

        // This method draws an arrow from one spot to another
        private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
            int arrowSize = 10;
            double angle = Math.atan2(y2 - y1, x2 - x1);
            int x1Edge = x1 + (int) (NODE_SIZE / 2 * Math.cos(angle));
            int y1Edge = y1 + (int) (NODE_SIZE / 2 * Math.sin(angle));
            int x2Edge = x2 - (int) (NODE_SIZE / 2 * Math.cos(angle));
            int y2Edge = y2 - (int) (NODE_SIZE / 2 * Math.sin(angle));

            // Draw the main line for the arrow
            g.drawLine(x1, y1, x2Edge, y2Edge);

            // Now add the arrowhead at the end
            int arrowX1 = x2Edge - (int) (arrowSize * Math.cos(angle - Math.PI / 6));
            int arrowY1 = y2Edge - (int) (arrowSize * Math.sin(angle - Math.PI / 6));
            int arrowX2 = x2Edge - (int) (arrowSize * Math.cos(angle + Math.PI / 6));
            int arrowY2 = y2Edge - (int) (arrowSize * Math.sin(angle + Math.PI / 6));

            g.drawLine(x2Edge, y2Edge, arrowX1, arrowY1);
            g.drawLine(x2Edge, y2Edge, arrowX2, arrowY2);
        }
    }

    // This panel shows the queue as a row of boxes
    static class QueueVisual extends JPanel {
        private Queue<Integer> queue;

        public QueueVisual(Queue<Integer> queue) {
            this.queue = queue;
            // Make sure there's enough room for all the queue elements and their labels
            setPreferredSize(new Dimension(queue.size() * (NODE_SIZE + GAP) + 2 * GAP, NODE_SIZE + 4 * GAP));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int x = GAP;
            int prevX = 0;
            int index = 0;
            Iterator<Integer> iterator = queue.iterator();
            while (iterator.hasNext()) {
                int value = iterator.next();
                g.drawRect(x, GAP, NODE_SIZE, NODE_SIZE);
                g.drawString(String.valueOf(value), x + NODE_SIZE / 2 - 3, GAP + NODE_SIZE / 2 + 5);

                // Draw a line connecting this box to the one before it
                if (index > 0) {
                    g.drawLine(prevX + NODE_SIZE, GAP + NODE_SIZE / 2, x, GAP + NODE_SIZE / 2);
                }

                prevX = x;
                x += NODE_SIZE + GAP;
                index++;
            }

            // Add labels to show which end is the front and which is the back
            if (queue.size() > 0) {
                g.drawString("Front (Dequeue)", GAP, GAP - 10);
                g.drawString("Back (Enqueue)", prevX - NODE_SIZE / 2, GAP - 10);
            }
        }
    }

    // This panel draws the stack as a vertical column
    static class StackVisual extends JPanel {
        private Stack<Integer> stack;

        public StackVisual(Stack<Integer> stack) {
            this.stack = stack;
            // Make the panel tall enough for all the stack elements
            setPreferredSize(new Dimension(NODE_SIZE + 2 * GAP, stack.size() * (NODE_SIZE + GAP) + GAP));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int y = GAP;
            Integer prevValue = null; // We'll use this to remember where the last box was

            for (Integer value : stack) {
                // Draw the box for this stack value
                g.drawRect(GAP, y, NODE_SIZE, NODE_SIZE);
                g.drawString(String.valueOf(value), GAP + NODE_SIZE / 2 - 3, y + NODE_SIZE / 2 + 5);

                // If this isn't the first box, draw a line from the one above to this one
                if (prevValue != null) {
                    int prevY = y - NODE_SIZE - GAP;
                    g.drawLine(GAP + NODE_SIZE / 2, prevY + NODE_SIZE, GAP + NODE_SIZE / 2, y);
                }

                // Remember where this box is for the next loop
                prevValue = value;
                y += NODE_SIZE + GAP;
            }
        }
    }

    // This method builds a binary tree from the user's input (level order, with
    // 'null' for missing nodes)
    private TreeNode createBinaryTreeFromInput(String[] elements) {
        if (elements.length == 0 || elements[0].equals("null"))
            return null;

        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.parseInt(elements[0]));
        queue.add(root);

        int index = 1;
        while (!queue.isEmpty() && index < elements.length) {
            TreeNode current = queue.poll();

            // Try to make the left child if possible
            if (index < elements.length && !elements[index].equals("null")) {
                current.left = new TreeNode(Integer.parseInt(elements[index]));
                queue.add(current.left);
            }
            index++;

            // Try to make the right child if possible
            if (index < elements.length && !elements[index].equals("null")) {
                current.right = new TreeNode(Integer.parseInt(elements[index]));
                queue.add(current.right);
            }
            index++;
        }

        return root;
    }

    // This panel draws the binary tree with circles and lines
    static class BinaryTreeVisual extends JPanel {
        private TreeNode root;

        public BinaryTreeVisual(TreeNode root) {
            this.root = root;
            setPreferredSize(new Dimension(800, 600));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTree(g, root, 400, 50, 200);
        }

        private void drawTree(Graphics g, TreeNode node, int x, int y, int xOffset) {
            if (node != null) {
                g.drawOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
                g.drawString(String.valueOf(node.val), x - 5, y + 5);

                if (node.left != null) {
                    int childX = x - xOffset;
                    int childY = y + GAP;
                    drawLine(g, x, y, childX, childY);
                    drawTree(g, node.left, childX, childY, xOffset / 2);
                }
                if (node.right != null) {
                    int childX = x + xOffset;
                    int childY = y + GAP;
                    drawLine(g, x, y, childX, childY);
                    drawTree(g, node.right, childX, childY, xOffset / 2);
                }
            }
        }

        // Helper method to draw lines between nodes
        private void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
            // Figure out where the line should start and end so it connects the circles
            // nicely
            int nodeRadius = NODE_SIZE / 2;
            double angle = Math.atan2(y2 - y1, x2 - x1);
            int x1Edge = x1 + (int) (nodeRadius * Math.cos(angle));
            int y1Edge = y1 + (int) (nodeRadius * Math.sin(angle));
            int x2Edge = x2 - (int) (nodeRadius * Math.cos(angle));
            int y2Edge = y2 - (int) (nodeRadius * Math.sin(angle));

            // Draw the line between the parent and child nodes
            g.drawLine(x1Edge, y1Edge, x2Edge, y2Edge);
        }
    }
}
