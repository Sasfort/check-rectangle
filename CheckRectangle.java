import java.io.*;
import java.util.*;

public class CheckRectangle {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static int count = 0;
    private static int[][] dots;
    private static int[][] combinations;

    public static void main(String[] args) {
        
        System.out.println("Masukkan jumlah titik yang ingin dicek:");
        int N = in.nextInt();

        dots = new int[N][2];
        int noDupliCount = 0;

        System.out.println();
        System.out.println("Masukkan koordinat dari titik-titik yang ingin dicek dengan format berikut:");
        System.out.println("x y");
        for (int i = 0; i < N; i++) {
            int x = in.nextInt();
            int y = in.nextInt();

            //Jika terdapat titik yang duplikat, maka tidak digunakan lebih dari 1 kali
            boolean duplicate = false;
            for (int j = 0; j < noDupliCount; j++) {
                if (x == dots[j][0] && y == dots[j][1]) {
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate) {
                dots[noDupliCount][0] = x;
                dots[noDupliCount][1] = y;
                noDupliCount++;
            }
        }

        //Menggunakan jumlah titik non duplikat sebagai N
        System.out.println();
        N = noDupliCount;

        int combin = nCr(N,4);
        combinations = new int[combin][4];
        
        if (N >= 4) {
            int[] order = new int[N];
            for (int i = 0; i < N;i++) {
                order[i] = i;
            }
            //Menyimpan semua kombinasi 4 titik yang mungkin
            allCombination(order, N, 4);

            boolean found = false;

            //Mengecek masing-masing kombinasi apakah dapat membentuk persegi panjang
            for (int i = 0; i < combin; i++) {
                if (checkRectangle(combinations[i])) {
                    found = true;
                    break;
                }
            }

            if (found) {
                out.println("Ya, set-set angka di atas dapat membentuk persegi panjang");
            } else {
                out.println("Tidak, set-set angka di atas tidak dapat membentuk persegi panjang");
            }
        } else {
            out.println("Tidak, set-set angka di atas tidak dapat membentuk persegi panjang");
        }

        out.close();
    }

    //Fungsi untuk mengecek apakah sebuah kombinasi titik dapat menghasilkan persegi panjang
    static boolean checkRectangle(int[] order) {
        int a = order[0];
        int b = order[1];
        int c = order[2];
        int d = order[3];
        return isRectangle(a, b, c, d) || isRectangle(b, c, a, d) || isRectangle(c, a, b, d);
    }

    //Mengecek apakah tiap sudut dari kombinasi titik-titik tegak lurus
    static boolean isRectangle(int a, int b, int c, int d){
        return isOrthogonal(a, b, c) && isOrthogonal(b, c, d) && isOrthogonal(c, d, a);
    }

    //Mengecek tegak lurusnya suatu kombinasi 3 titik
    static boolean isOrthogonal(int a, int b, int c) {
        return (dots[b][0] - dots[a][0]) * (dots[b][0] - dots[c][0]) + (dots[b][1] - dots[a][1]) * (dots[b][1] - dots[c][1]) == 0;
    }

    //Mencari semua kombinasi dari titik-titik
    static void allCombination(int arr[], int n, int r) {
        int data[] = new int[r];
        combinationUtil(arr, n, r, 0, data, 0);
    }

    //Pemanggilan rekursif untuk mencari kombinasi
    static void combinationUtil(int arr[], int n, int r, int index, int data[], int i) {
        if (index == r) {
            for (int j = 0; j < r; j++) {
                combinations[count][j] = data[j];
            }
            count++;
        } else if (i < n) {
            data[index] = arr[i];
            combinationUtil(arr, n, r, index+1, data, i+1);
            combinationUtil(arr, n, r, index, data, i+1);
        }
    }

    //Menghitung jumlah kombinasi nCr
    static int nCr(int n, int r) {
        return fact(n) / (fact(r) * fact(n - r));
    }
 
    //Menghitung n faktorial
    static int fact(int n) {
        int res = 1;
        for (int i = 2; i <= n; i++) {
            res = res * i;
        }
        return res;
    }

    
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
    }
}