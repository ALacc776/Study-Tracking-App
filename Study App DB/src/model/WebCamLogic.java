package model;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

//for timer
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainWindow;
public class WebCamLogic extends Thread {

    private JPanel panel;
    private JLabel label;

    private boolean faceDetected = false;
    private int noFaceCounter = 0;  
    // Counter for the number of seconds no face is detected
    

    public WebCamLogic() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        label = new JLabel();
        panel.add(label, BorderLayout.CENTER);

    }

    @Override
    public void run() {
        VideoCapture capture = new VideoCapture(0);
        CascadeClassifier faceDetector = new CascadeClassifier("C:\\Users\\qwer\\Desktop\\"
        		+ "opencv3.4.0\\build\\etc\\haarcascades\\haarcascade_frontalface_alt.xml");

        while (true) {
            Mat mat = new Mat();
            if (!capture.read(mat)) {
                System.out.println("Failed to read frame.");
                break;
            }

            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY);

            MatOfRect faces = new MatOfRect();
            faceDetector.detectMultiScale(mat, faces, 1.1, 2, 0, new Size(30, 30), new Size());

            if (faces.toArray().length > 0) {
                noFaceCounter = 0;
                faceDetected = true;

                for (Rect rect : faces.toArray()) {
                    Imgproc.rectangle(mat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, 
                    		rect.y + rect.height), new Scalar(0, 255, 0), 2);
                }
            } else {
                noFaceCounter++;
                if (noFaceCounter >= 10) {  // No face detected for 10 seconds
                    faceDetected = false;
                }
            }

            BufferedImage image = matToBufferedImage(mat);

            Image scaledImage = image.getScaledInstance(394, 258, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            label.setIcon(imageIcon);
            panel.revalidate();
        }
    }
    // Code adapted from:
    // https://www.youtube.com/watch?v=cDUNpBmymXw (Face and Eye Detection using OpenCV with Java - Real Time Camera)
    // https://docs.opencv.org/3.4/d2/d14/tutorial_js_object_detection_with_camera.html


    public JPanel getPanel() {
        return panel;
    }

    public boolean isFaceDetected() {
        return faceDetected;
    }

    // HELPED WITH GITHUB PAGE -- add ciation
    private BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] bytes = new byte[bufferSize];
        mat.get(0, 0, bytes);
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(bytes, 0, targetPixels, 0, bytes.length);
        return image;
    }
    // HELPED WITH GITHUB PAGE 

 
}


