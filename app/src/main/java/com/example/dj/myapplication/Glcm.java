package com.example.dj.myapplication;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Vector;

import static java.lang.Math.abs;
import static java.lang.Math.log10;
import static org.opencv.core.CvType.CV_32FC1;

public class Glcm {
    public static Vector<Double> glcm(Mat img)
    {
        double e=0, sh = 0, mux = 0, muy = 0, mu = 0, var = 0, energy = 0, contrast = 0, homogenity = 0, IDM = 0, entropy = 0, mean1 = 0;
        double energy1 = 0, sh1 = 0, mux1 = 0, muy1 = 0, mu1 = 0, var1 = 0, contrast1 = 0, homogenity1 = 0, IDM1 = 0, entropy1 = 0, mean11 = 0;
        double energy2 = 0, sh2 = 0, mux2 = 0, muy2 = 0, mu2 = 0, var2 = 0, contrast2 = 0, homogenity2 = 0, IDM2 = 0, entropy2 = 0, mean12 = 0;
        double energy3 = 0, sh3 = 0, mux3 = 0, muy3 = 0, mu3 = 0, var3 = 0,  contrast3 = 0, homogenity3 = 0, IDM3 = 0, entropy3 = 0, mean13 = 0;
        double energy4 = 0, sh4 = 0, mux4 = 0, muy4 = 0, mu4 = 0, var4 = 0,  contrast4 = 0, homogenity4 = 0, IDM4 = 0, entropy4 = 0, mean14 = 0;
        int row = img.rows(), col = img.cols();

        Mat gl = Mat.zeros(256, 256, CV_32FC1);
        Mat gl1 = Mat.zeros(256, 256, CV_32FC1);
        Mat gl2 = Mat.zeros(256, 256, CV_32FC1);
        Mat gl3 = Mat.zeros(256, 256, CV_32FC1);
        Mat gl4 = Mat.zeros(256, 256, CV_32FC1);
        //creating glcm matrix with 256 levels,radius=1 and in the horizontal direction
        for (int i = 0; i<row; i++) {
            for (int j = 0; j < col - 1; j++){
                int first= (int)img.get(i,j)[0];
                int next = (int)img.get(i,j+1)[0];
                double[] prev =gl.get((int)first,(int)next);
                prev[0]++;
                gl.put((int)first,(int)next,prev);
            }
        }
        Mat glt = gl.clone();
        Core.transpose(gl, glt);
        Core.add(gl, glt, gl);
        Scalar sum = Core.sumElems(gl);
        Core.divide(gl, sum, gl);

                //gl.get(img.get(i, j), img.get(i, j + 1)) = gl.at<double>(img.at<uchar>(i, j), img.at<uchar>(i, j + 1)) + 1;

        for (int i = 0; i<row/2; i++) {
            for (int j = 0; j < (col - 1) / 2; j++) {
                int first= (int)img.get(i,j)[0];
                int next = (int)img.get(i,j+1)[0];
                double[] prev =gl1.get((int)first,(int)next);
                prev[0]++;
                gl1.put((int)first,(int)next,prev);
            }
        }
        Mat glt1 = gl1.clone();
        Core.transpose(gl1, glt1);
        Core.add(gl1, glt1, gl1);
        Scalar sum1 = Core.sumElems(gl1);
        Core.divide(gl1, sum1, gl1);
                //gl1.at<double>(img.at<uchar>(i, j), img.at<uchar>(i, j + 1)) = gl1.at<double>(img.at<uchar>(i, j), img.at<uchar>(i, j + 1)) + 1;

        for (int i = row/2; i<row; i++){
            for (int j = 0; j<(col - 1)/2; j++){
                int first= (int)img.get(i,j)[0];
                int next = (int)img.get(i,j+1)[0];
                double[] prev =gl2.get((int)first,(int)next);
                prev[0]++;
                gl2.put((int)first,(int)next,prev);
            }}

        Mat glt2 = gl2.clone();
        Core.transpose(gl2, glt2);
        Core.add(gl2, glt2, gl2);
        Scalar sum2 = Core.sumElems(gl2);
        Core.divide(gl2, sum2, gl2);

                //gl2.at<double>(img.at<uchar>(i, j), img.at<uchar>(i, j + 1)) = gl2.at<double>(img.at<uchar>(i, j), img.at<uchar>(i, j + 1)) + 1;

        for (int i = row/2; i<row; i++) {
            for (int j = (col - 1) / 2; j < (col - 1); j++) {
                int first= (int)img.get(i,j)[0];
                int next = (int)img.get(i,j+1)[0];
                double[] prev =gl3.get((int)first,(int)next);
                prev[0]++;
                gl3.put((int)first,(int)next,prev);
            }
        }
        Mat glt3 = gl3.clone();
        Core.transpose(gl3, glt3);
        Core.add(gl3, glt3, gl3);
        Scalar sum3 = Core.sumElems(gl3);
        Core.divide(gl3, sum3, gl3);
                //gl3.at<double>(img.at<uchar>(i, j), img.at<uchar>(i, j + 1)) = gl3.at<double>(img.at<uchar>(i, j), img.at<uchar>(i, j + 1)) + 1;

        for (int i = 0; i<row/2; i++) {
            for (int j = (col - 1) / 2; j < (col - 1); j++) {
                int first= (int)img.get(i,j)[0];
                int next = (int)img.get(i,j+1)[0];
                double[] prev =gl4.get((int)first,(int)next);
                prev[0]++;
                gl4.put((int)first,(int)next,prev);
            }
        }
        Mat glt4 = gl4.clone();
        Core.transpose(gl4, glt4);
        Core.add(gl4, glt4, gl4);
        Scalar sum4 = Core.sumElems(gl4);
        Core.divide(gl4, sum4, gl4);
                //gl4.at<double>(img.at<uchar>(i, j), img.at<uchar>(i, j + 1)) = gl4.at<double>(img.at<uchar>(i, j), img.at<uchar>(i, j + 1)) + 1;

        // normalizing glcm matrix for parameter determination
    /*gl = gl + gl.t();
    gl = gl / sum(gl)[0];

	gl1 = gl1 + gl1.t();
    gl1 = gl1 / sum(gl1)[0];

    gl2 = gl2 + gl2.t();
    gl2 = gl2 / sum(gl2)[0];

    gl3 = gl3 + gl3.t();
    gl3 = gl3 / sum(gl3)[0];

    gl4 = gl4 + gl4.t();
    gl4 = gl4 / sum(gl4)[0];*/

        for (int i = 0; i<256; i++)
        {
            for (int j = 0; j<256; j++)
            {
                double[] first = gl.get(i,j);
                e = (double) first[0]*(double) first[0];
                //vec_energy.add((double) e);
                energy += e;
                mux += (double) i*(double)first[0];
                muy += j*(double)first[0];
                mu += (double)first[0];
                mu /= (256*256);
                //finding parameters
                sh += (i+j-mux-muy)*(i+j-mux-muy)*(i+j-mux-muy)*(double)first[0]/(256*256);
                contrast = contrast + (i - j)*(i - j)*(double)first[0];
                homogenity = homogenity + (double)first[0] / (1 + abs(i - j));
                var += (i - mu)*(i - mu)*(double)first[0]/(256*256);
                if (i != j)
                    IDM = IDM + (double)first[0] / ((i - j)*(i - j));                      //Taking k=2;
                if ((double)first[0] != 0)
                entropy = (double) (entropy - (double)first[0]*log10((double)first[0]));
                mean1 = (double)(mean1 + 0.5*(i*(double)first[0]+ j*(double)first[0]));
            }

        }

        for (int i = 0; i<256; i++)
            for (int j = 0; j<256; j++)
            {
                double[] first = gl1.get(i,j);
                energy1 += (double)first[0]*(double)first[0];
                //vec_energy.push_back(energy);
                mux1 += i*(double)first[0];
                muy1 += j*(double)first[0];
                mu1 += (double)first[0];
                mu1 /= (256*256);
                //finding parameters
                sh1 += (i+j-mux1-muy1)*(i+j-mux1-muy1)*(i+j-mux1-muy1)*(double)first[0]/(256*256);
                var1 += (i - mu1)*(i - mu1)*(double)first[0]/(256*256);
                contrast1 = contrast1 + (i - j)*(i - j)*(double)first[0];
                homogenity1 = homogenity1 + (double)first[0] / (1 + abs(i - j));
                if (i != j)
                    IDM1 = IDM1 + (double)first[0] / ((i - j)*(i - j));                      //Taking k=2;
                if ((double)first[0] != 0)
                entropy1 = (double) (entropy1 - (double)first[0]*log10((double)first[0]));
                mean11 = (double) (mean11 + 0.5*(i*(double)first[0] + j*(double)first[0]));
            }

        for (int i = 0; i<256; i++)
            for (int j = 0; j<256; j++)
            {
                double[] first = gl2.get(i,j);
                energy2 += (double)first[0]*(double)first[0];
                //vec_energy.push_back(energy);
                mux2 += i*(double)first[0];
                muy2 += j*(double)first[0];
                mu2 += (double)first[0];
                mu2 /= (256*256);
                //finding parameters
                sh2 += (i+j-mux2-muy2)*(i+j-mux2-muy2)*(i+j-mux2-muy2)*(double)first[0]/(256*256);
                var2 += (i - mu2)*(i - mu2)*(double)first[0]/(256*256);
                contrast2 = contrast2 + (i - j)*(i - j)*(double)first[0];
                homogenity2 = homogenity2 + (double)first[0] / (1 + abs(i - j));
                if (i != j)
                    IDM2 = IDM2 + (double)first[0] / ((i - j)*(i - j));                      //Taking k=2;
                if ((double)first[0] != 0)
                entropy2 = (double) (entropy2 - (double)first[0]*log10((double)first[0]));
                mean12 = (double) (mean12 + 0.5*(i*(double)first[0] + j*(double)first[0]));
            }

        for (int i = 0; i<256; i++)
            for (int j = 0; j<256; j++)
            {
                double[] first = gl3.get(i,j);
                energy3 += (double)first[0] *(double)first[0] ;
                //vec_energy.push_back(energy);
                mux3 += i*(double)first[0] ;
                muy3 += j*(double)first[0] ;
                mu3 += (double)first[0] ;
                mu3 /= (256*256);
                //finding parameters
                sh3 += (i+j-mux3-muy3)*(i+j-mux3-muy3)*(i+j-mux3-muy3)*(double)first[0] /(256*256);
                var3 += (i - mu3)*(i - mu3)*(double)first[0] /(256*256);
                contrast3 = contrast3 + (i - j)*(i - j)*(double)first[0] ;
                homogenity3 = homogenity3 + (double)first[0] / (1 + abs(i - j));
                if (i != j)
                    IDM3 = IDM3 + (double)first[0]  / ((i - j)*(i - j));                      //Taking k=2;
                if ((double)first[0]  != 0)
                entropy3 = (double) (entropy3 - (double)first[0] *log10((double)first[0] ));
                mean13 = (double) (mean13 + 0.5*(i*(double)first[0]  + j*(double)first[0] ));
            }

        for (int i = 0; i<256; i++)
            for (int j = 0; j<256; j++)

              {
                  double[] first = gl4.get(i,j);
                energy4 += (double)first[0] *(double)first[0] ;
                //vec_energy.push_back(energy);
                mux4 += i*(double)first[0] ;
                muy4 += j*(double)first[0] ;
                mu4 += (double)first[0] ;
                mu4 /= (256*256);
                //finding parameters
                sh4 += (i+j-mux4-muy4)*(i+j-mux4-muy4)*(i+j-mux4-muy4)*(double)first[0] /(256*256);
                var4 += (i - mu4)*(i - mu4)*(double)first[0] /(256*256);
                contrast4 = contrast4 + (i - j)*(i - j)*(double)first[0] ;
                homogenity4 = homogenity4 + (double)first[0]  / (1 + abs(i - j));
                if (i != j)
                    IDM4 = IDM4 + (double)first[0] / ((i - j)*(i - j));                      //Taking k=2;
                if ((double)first[0]  != 0)
                entropy4 = (double) (entropy4 - (double)first[0] *log10((double)first[0]) );
                mean14 = (double) (mean14 + 0.5*(i*(double)first[0]  + j*(double)first[0] ));
            }

    /*  for (int i = 0; i<256; i++)
    {
    for (int j = 0; j<256; j++)
    cout << a[i][j] << "\t";
    cout << endl;
    }*/
        //vector<double> attributes(40,0);
        Vector<Double> attributes = new Vector<Double>();
        for(int i=0;i<40;i++)
            attributes.add((double)0);
        //fstream fout;
        //fout.open ("Data.csv", ios::out | ios::app);
        //.set(
        //cout << "energy=" << energy << endl;

        System.out.print(homogenity2);

        attributes.set( 0, (double)energy);
        attributes.set(8, (double)energy1);
        attributes.set(16, (double)energy2);
        attributes.set(24,(double)energy3);
        attributes.set(32,(double)energy4);
        //cout << "contrast=" << contrast << endl;
        attributes.set(1, (double)contrast);
        attributes.set(9, (double)contrast1);
        attributes.set(17,(double) contrast2);
        attributes.set(25,(double)contrast3);
        attributes.set(33, (double)contrast4);
        //cout << "homogenity=" << homogenity << endl;
        attributes.set(2, (double)homogenity);
        attributes.set(10, (double)homogenity1);
        attributes.set(18, (double)homogenity2);
        attributes.set(26, (double)homogenity3);
        attributes.set(34, (double)homogenity4);
       // cout << "IDM=" << IDM << endl;
        attributes.set(3, (double)IDM);
        attributes.set(11, (double)IDM1);
        attributes.set(19, (double)IDM2);
        attributes.set(27,(double) IDM3);
        attributes.set(35, (double)IDM4);
        //cout << "entropy=" << entropy << endl;
        attributes.set(4,(double) entropy);
        attributes.set(12, (double)entropy1);
        attributes.set(20,(double)entropy2);
        attributes.set(28, (double)entropy3);
        attributes.set(36, (double)entropy4);
        //cout << "mean=" << mean1 << endl;
        attributes.set(5, (double)mean1);
        attributes.set(13, (double)mean11);
        attributes.set(21, (double)mean12);
        attributes.set(29, (double)mean13);
        attributes.set(37, (double)mean14);
        //cout << "variance=" << var << endl;
        attributes.set(6, (double)var);
        attributes.set(14, (double)var1);
        attributes.set(22, (double)var2);
        attributes.set(30, (double)var3);
        attributes.set(38,(double) var4);
        //cout<< "Cluster Shade=" << sh << endl;
        attributes.set(7, (double)sh);
        attributes.set(15, (double)sh1);
        attributes.set(23, (double)sh2);
        attributes.set(31, (double)sh3);
        attributes.set(39, (double)sh4);
       // attributes.add(0,img.get(0,0)[0]);
        //for(int i =0;i<40;i++)
        //fout << attributes[i] << "," ;
        //fout<<endl;
	/*for (int i = 0; i<256; i++)
	{
	        for (int j = 0; j<256; j++)
		{
			//cout<<gl.at<double>(i, j)<<" ";
		}
		//cout<<endl;
	}*/
      /*attributes.add(energy);
      attributes.add(homogenity);
      attributes.add(var);
      attributes.add(gl.get(115,155)[0]);*/
        return attributes;
    }

}
