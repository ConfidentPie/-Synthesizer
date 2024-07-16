import javax.sound.midi.*;
import java.util.Scanner;

public class Main {
    public static  void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        System.out.println("Hello World!");

        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        Receiver receiver = synthesizer.getReceiver();

        ShortMessage message = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 10, 127);
        receiver.send(message, -1);

        System.out.println("Hi, please enter notes:");
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        while (!input.equals("q")) {
            for (int i = 0; i < input.length(); i++) {
                char note = input.charAt(i);
                int noteCode = convertNote(note);
                playNote(receiver, note, 500);
            }

            System.out.println("Please enter notes:");
            input = scanner.nextLine();
        }

        scanner.close();
        synthesizer.close();
        System.out.println("Goodbye!");
    }

    static int convertNote(char note) {
       return switch (note) {
            case 'C' -> 60;
            case 'D' -> 62;
            case 'E' -> 64;
            case 'F' -> 66;
            case 'G' -> 67;
            case 'A' -> 69;
            case 'B' -> 71;
            default -> 110;
        };
    }

    static void playNote(Receiver receiver, int noteCode, int duration) throws InvalidMidiDataException, InterruptedException {
        receiver.send(
                new ShortMessage(ShortMessage.NOTE_ON, noteCode, 127),
                -1
        );

        Thread.sleep(duration);
        receiver.send(
                new ShortMessage(ShortMessage.NOTE_OFF, noteCode, 0),
                -1
        );
    }

}
