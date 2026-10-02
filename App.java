import java.io.*;

public class App {

    public static double calculateDiscount(
            double price, String category) {

        switch (category) {

            case "electronics":
                return price * 0.9;

            case "clothing":
                return price * 0.8;

            default:
                return price;
        }
    }

    public static void createCsvFile() throws IOException {

        try (PrintWriter pw =
                new PrintWriter(new FileWriter("batch_data.csv"))) {

            pw.println("Price,Category,ExpectedResult");
            pw.println("500,electronics,450");
            pw.println("300,clothing,240");
            pw.println("150,grocery,150");
            pw.println("1000,electronics,900");
        }
    }

    public static void dataDrivenBatch()
            throws IOException {

        System.out.println(
            "---- Data Driven Batch Test ----"
        );

        int passCount = 0;
        int failCount = 0;

        try (BufferedReader br =
                new BufferedReader(
                    new FileReader("batch_data.csv"))) {

            br.readLine();

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                double price =
                    Double.parseDouble(parts[0]);

                String category = parts[1];

                double expected =
                    Double.parseDouble(parts[2]);

                double actual =
                    calculateDiscount(price, category);

                if (actual == expected) {

                    System.out.printf(
                        "PASS: %s, price=%.1f -> %.1f%n",
                        category, price, actual
                    );

                    passCount++;

                } else {

                    System.out.printf(
                        "FAIL: %s, price=%.1f -> got %.1f, expected %.1f%n",
                        category, price, actual, expected
                    );

                    failCount++;
                }
            }
        }

        System.out.println(
            "\nBatch Summary: " +
            passCount + " Passed, " +
            failCount + " Failed"
        );
    }

    public static void main(String[] args)
            throws IOException {

        createCsvFile();

        dataDrivenBatch();
    }
}
