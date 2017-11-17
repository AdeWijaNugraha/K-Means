
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author adewijanugraha
 */
public class KMeans {

    public static void main(String[] args) {
        double[][] data = {
            {-8.109, 112.917, 311, 73},
            {-2.584, 121.378, 310.5, 71},
            {0.107, 117.471, 311.3, 74},
            {-8.346, 147.832, 334.4, 86},
            {-8.348, 147.822, 311.6, 50},
            {-2.566, 101.254, 323.5, 77},
            {0.107, 117.473, 313, 81},
            {-8.106, 112.927, 312.1, 78},
            {4.019, 117.308, 325.6, 79},
            {2.817, 113.299, 318.4, 45},
            {2.817, 113.293, 317.9, 37},
            {2.793, 112.135, 335.1, 87},
            {2.783, 112.144, 318.7, 70},
            {2.701, 112.136, 330, 63},
            {0.58, 101.981, 311.3, 35},
            {-7.589, 138.561, 314.1, 60},
            {-8.254, 113.37, 310.8, 43},
            {-2.394, 110.703, 326.3, 79},
            {-2.39, 110.708, 318.5, 70},
            {-2.391, 110.697, 317.4, 65},
            {2.689, 113.127, 314.2, 40},
            {3.836, 113.566, 325.6, 68},
            {-4.203, 122.195, 314.4, 57},
            {3.009, 101.374, 315.7, 0},
            {-1.038, 123.29, 316.5, 57},
            {-0.798, 123.108, 312.1, 52},
            {-9.398, 119.737, 323.8, 40},
            {-8.112, 118.079, 319.8, 0},
            {-8.113, 118.07, 319.8, 51},
            {-6.579, 146.219, 315.5, 54},
            {-2.58, 121.389, 310.7, 39},
            {0.568, 102.16, 312.5, 51},
            {0.275, 102.341, 315.7, 52},
            {-2.517, 104.245, 323.6, 77},
            {-3.693, 104.396, 317, 40},
            {-3.721, 104.375, 350.3, 95},
            {-3.722, 104.386, 321.2, 65},
            {-3.532, 127.947, 317.2, 51},
            {-3.534, 127.938, 321, 56},
            {-4.546, 104.317, 312.6, 55},
            {-4.547, 104.307, 312.7, 55},
            {-3.719, 104.38, 318.9, 53},
            {-3.594, 104.28, 311, 31},
            {-3.481, 103.471, 315.4, 60},
            {-2.817, 103.541, 315.6, 44},
            {-1.396, 102.544, 310.2, 24},
            {0.553, 102.297, 317, 41},
            {13.353, 120.572, 314.4, 61},
            {10.365, 123.033, 312.3, 51}
        };

        Scanner masuk = new Scanner(System.in);

        System.out.println("-------------------- K-Means --------------------\n");
        System.out.print("Masukkan Nilai K : ");
        int k = masuk.nextInt();
        System.out.print("Masukkan Nilai Threshold : ");
        double threshold = masuk.nextDouble();

        double[][] dataNormal = new double[data.length][data[0].length];
        int[][] kelompok = new int[dataNormal.length][2];

        double[] min = new double[data[0].length];
        double[] max = new double[data[0].length];

        double[] minJarak = new double[dataNormal.length];
        double[] maxJarak = new double[dataNormal.length];
        int[][] cluster = new int[k][2];

        double fBaru = 0;
        double fLama = 0;
        double deltaF = 0;

        double[][] jarak = new double[dataNormal.length][cluster.length];
        double[][] centroid = new double[cluster.length][dataNormal[0].length];

        for (int i = 0; i < min.length; i++) {
            min[i] = 1000;
            max[i] = 0;
        }

        int namaKelompok = 1;
        for (int i = 0; i < cluster.length; i++) {
            cluster[i][0] = namaKelompok;
            namaKelompok++;
        }

        //Normalisasi Data
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                for (int l = 0; l < data.length; l++) {
                    //Cari Min
                    if (data[l][j] < min[j]) {
                        min[j] = data[l][j];
                    }
                    //Cari Max
                    if (data[l][j] > max[j]) {
                        max[j] = data[l][j];
                    }
                }
                dataNormal[i][j] = (data[i][j] - min[j]) / (max[j] - min[j]);
            }
        }

        //Hasil Normalisasi Data
        System.out.println("\nData Normal :");
        for (int i = 0; i < dataNormal.length; i++) {
            for (int j = 0; j < dataNormal[0].length; j++) {
                System.out.print(dataNormal[i][j] + "\t");
            }
            System.out.println("");
        }

        //Acak Data
        int noKelompok = 1;
        for (int i = 0; i < kelompok.length; i++) {
            for (int j = 0; j < kelompok[0].length; j++) {
                if (noKelompok <= k) {
                    kelompok[i][j] = noKelompok;
                } else {
                    noKelompok = 1;
                    kelompok[i][j] = noKelompok;
                }
            }
            noKelompok++;
        }

        //Hasil Acak No Kelompok Tiap Data
        System.out.println("\nNo Kelompok Awal Tiap Data :");
        for (int i = 0; i < kelompok.length; i++) {
            for (int j = 0; j < kelompok[0].length; j++) {
                System.out.print(kelompok[i][j] + "\t");
            }
            System.out.println("");
        }

        do {
            //Hitung Total K
            for (int i = 0; i < cluster.length; i++) {
                cluster[i][1] = 0;
            }

            for (int i = 0; i < cluster.length; i++) {
                for (int j = 0; j < kelompok.length; j++) {
                    if (kelompok[j][0] == cluster[i][0]) {
                        cluster[i][1] += 1;
                    }
                }
            }

            //Hasil Total K
            System.out.println("\nData Total K :");
            for (int i = 0; i < cluster.length; i++) {
                for (int j = 0; j < cluster[0].length; j++) {
                    System.out.print(cluster[i][j] + "\t");
                }
                System.out.println("");
            }

            //Hitung total KF
            for (int i = 0; i < centroid[0].length; i++) {
                for (int j = 0; j < cluster.length; j++) {
                    for (int l = 0; l < kelompok.length; l++) {
                        if (kelompok[l][0] == cluster[j][0]) {
                            centroid[j][i] += dataNormal[l][i];
                        }
                    }
                }
            }

            //Hasil Total KF
            System.out.println("\nData Total KF :");
            for (int i = 0; i < centroid.length; i++) {
                for (int j = 0; j < centroid[0].length; j++) {
                    System.out.print(centroid[i][j] + "\t");
                }
                System.out.println("");
            }

            //Menghitung Centroid (Total KF/ Total K)
            for (int i = 0; i < centroid.length; i++) {
                for (int j = 0; j < centroid[0].length; j++) {
                    centroid[i][j] /= cluster[i][1];
                }
            }

            //Hasil Centroid
            System.out.println("\nData Centroid :");
            for (int i = 0; i < centroid.length; i++) {
                for (int j = 0; j < centroid[0].length; j++) {
                    System.out.print(centroid[i][j] + "\t");
                }
                System.out.println("");
            }

            //Menghitung Jarak 
            for (int i = 0; i < jarak.length; i++) {
                for (int j = 0; j < jarak[0].length; j++) {
                    for (int l = 0; l < dataNormal[0].length; l++) {
                        jarak[i][j] += Math.pow(dataNormal[i][l] - centroid[j][l], 2);
                    }
                    jarak[i][j] = Math.sqrt(jarak[i][j]);
                }
            }

            //Hasil Jarak
            System.out.println("\nJarak :");
            for (int i = 0; i < jarak.length; i++) {
                for (int j = 0; j < jarak[0].length; j++) {
                    System.out.print(jarak[i][j] + "\t");
                }
                System.out.println("");
            }

            //Jarak minimum
            for (int i = 0; i < minJarak.length; i++) {
                minJarak[i] = 1000;
            }

            for (int i = 0; i < jarak.length; i++) {
                kelompok[i][1] = kelompok[i][0];
                for (int j = 0; j < jarak[0].length; j++) {
                    //Cari Min
                    if (jarak[i][j] < minJarak[i]) {
                        minJarak[i] = jarak[i][j];
                        //Kelompok Terdekat
                        kelompok[i][0] = j + 1;
                    }
                }
            }

            //Hasil Jarak
            System.out.println("\nMin Jarak :");
            for (int i = 0; i < minJarak.length; i++) {
                System.out.println(minJarak[i]);
            }

            //Hasil Kelompok Baru
            System.out.println("\nNo Kelompok Baru Tiap Data :");
            for (int i = 0; i < kelompok.length; i++) {
                for (int j = 0; j < kelompok[0].length; j++) {
                    System.out.print(kelompok[i][j] + "\t");
                }
                System.out.println("");
            }

            //Menghitung Total F
            for (int i = 0; i < minJarak.length; i++) {
                fBaru += minJarak[i];
            }

            //Hasil F baru dan lama
            System.out.println("\nF Baru = " + fBaru + " F Lama = " + fLama);

            //Menghitung Delta F
            deltaF = fBaru - fLama;
            if (deltaF < 0) {
                deltaF *= -1;
            }

            //Pindah F
            fLama = fBaru;
            fBaru = 0;

            //Hasil F baru dan lama
            System.out.println("Delta F = " + deltaF);
        } while (deltaF > threshold);

        System.out.println("\nKesimpulan :");
        for (int i = 0; i < kelompok.length; i++) {
            System.out.println("Data " + (i + 1) + " Termasuk Kelompok " + kelompok[i][0]);
        }
    }
}