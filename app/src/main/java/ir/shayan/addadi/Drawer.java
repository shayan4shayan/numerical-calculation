package ir.shayan.addadi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kotlin.Pair;


public class Drawer extends View {
    private List<Pair<Float, Float>> points = new ArrayList<>();

    Paint paint;
    private List<Pair<Double, Double>> tmpPoints;

    double xMin = 0;
    double xMax = 0;
    double yMin = 0;
    double yMax = 0;

    public Drawer(Context context) {
        super(context);
        setup();
    }

    private void setup() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("Drawer", "" + tmpPoints.size());

                xMin = tmpPoints.get(0).getFirst();
                xMax = tmpPoints.get(tmpPoints.size() - 1).getFirst();

                Log.d("drawer", "" + (xMax - xMin));

                yMin = getYMin(tmpPoints);
                yMax = getYMax(tmpPoints);

                Log.d("Drawer", "" + (yMax - yMin));

                points.clear();

                for (Pair<Double, Double> pair : tmpPoints) {
                    points.add(calculateNewPosition(pair, xMax - xMin, yMax - yMin, xMin, yMin));
                }
            }
        });
    }

    public Drawer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public Drawer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public Drawer(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    public void setDrawPoints(List<Pair<Double, Double>> points) {
        this.tmpPoints = points;

        invalidate();
    }

    private Pair<Float, Float> calculateNewPosition(Pair<Double, Double> pair, double xLen,
                                                    double yLen, double xMin, double yMin) {
        Log.d("drawer", "x : " + pair.getFirst() + " y: " + pair.getSecond());
        float newX = (float) (((pair.getFirst() / xLen) * getWidth() - (xMin / xLen) * getWidth()));
        float newY = (float) (((pair.getSecond() / yLen) * getHeight() - (yMin / yLen) * getHeight()));
        newY = (-newY + getHeight());
        Log.d("Drawer", "x : " + newX + " y: " + newY);
        return new Pair<>(newX, newY);
    }

    private double getYMax(List<Pair<Double, Double>> points) {
        double max = Double.MIN_VALUE;
        for (Pair<Double, Double> pair : points) {
            if (pair.getSecond() > max) max = pair.getSecond();
        }
        return max;
    }

    private double getYMin(List<Pair<Double, Double>> points) {
        double min = Double.MAX_VALUE;
        for (Pair<Double, Double> pair : points) {
            if (pair.getSecond() < min) {
                min = pair.getSecond();
            }
        }
        return min;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCoordinates(canvas);
        drawFunction(canvas);
    }

    private void drawCoordinates(Canvas canvas) {
        float xStep = (float) ((xMax - xMin) / 10f);
        float yStep = (float) ((yMax - yMin) / 10f);

        float depth = 20f;

        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(3f);
        paint.setAlpha(0xaa);
        paint.setTextSize(24f);

        //drawing background lines
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);


        for (int i = 0; i < 10; i++) {
            float xPos = (getWidth() / 10) * i;
            float yPos = getHeight() / 2;
            canvas.drawLine(xPos, yPos - depth, xPos, yPos + depth, paint);
            canvas.drawText(String.format(Locale.US, "%.1f", (xMin + (i * xStep))),
                    xPos, yPos - depth - depth, paint);
        }

        for (int i = 0; i < 10; i++) {
            float xPos = getWidth() / 2;
            float yPos = (getHeight() / 10) * i;
            canvas.drawLine(xPos - depth, yPos, xPos + depth, yPos, paint);
            canvas.drawText(String.format(Locale.US, "%.1f", (yMax - (i * yStep))),
                    xPos + depth + depth, yPos, paint);
        }
    }

    private void drawFunction(Canvas canvas) {
        paint.setAlpha(0);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5f);

        for (int i = 0; i < points.size() - 1; i++) {
            canvas.drawLine(
                    points.get(i).getFirst(),
                    points.get(i).getSecond(),
                    points.get(i + 1).getFirst(),
                    points.get(i + 1).getSecond(),
                    paint);
        }
    }
}
