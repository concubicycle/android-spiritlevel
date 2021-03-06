package com.footbits.sava.oglspiritleveldisplay;


import android.graphics.drawable.shapes.Shape;

import java.util.ArrayList;


public class ShapeBuilder
{
    private ArrayList<Float> vertices;
    private ArrayList<Integer> indices;
    private boolean useHomogeneousCoordinates;
    private int floatsPerVertex;

    public ShapeBuilder(boolean useHomogeneousCoordinates)
    {
        vertices = new ArrayList<Float>();
        indices = new ArrayList<Integer>();

        this.useHomogeneousCoordinates = useHomogeneousCoordinates;

        if (this.useHomogeneousCoordinates)
            floatsPerVertex = 4;
        else
            floatsPerVertex = 3;
    }

    public ShapeBuilder addQuad(
            Float lrx, Float lry, Float lrz, //lower right
            Float urx, Float ury, Float urz, //upper right
            Float ulx, Float uly, Float ulz, //upper left
            Float llx, Float lly, Float llz) //lower left
    {
        int currentVertex = vertices.size()/floatsPerVertex;

        addVertex(lrx, lry, lrz);
        addVertex(urx, ury, urz);
        addVertex(ulx, uly, ulz);
        addVertex(llx, lly, llz);

        indices.add(currentVertex);
        indices.add(currentVertex+1);
        indices.add(currentVertex+2);

        indices.add(currentVertex);
        indices.add(currentVertex+2);
        indices.add(currentVertex+3);

        return this;
    }

    public ShapeBuilder addTriangle(
            Float lrx, Float lry, Float lrz, //lower right
            Float ux, Float uy, Float uz, //upper
            Float llx, Float lly, Float llz) //lower left
    {
        int currentSize = vertices.size() / floatsPerVertex;

        addVertex(lrx, lry, lrz);
        addVertex(ux, uy, uz);
        addVertex(llx, lly, llz);

        indices.add(currentSize);
        indices.add(currentSize + 1);
        indices.add(currentSize + 2);

        return this;
    }

    public void reset()
    {
        vertices.clear();
        indices.clear();
    }

    public float[] getVertices()
    {
        return toFloatArray(vertices);
    }

    public int[] getIndicesInt()
    {
        return toIntArray(indices);
    }

    public Mesh toMesh() {
        return new Mesh(getVertices(), getIndicesInt());
    }

    public short[] getIndicesShort()
    {
        return toShortArray(indices);
    }

    public int getNumVertexFloats()
    {
        return vertices.size();
    }

    public int getNumIndexInts()
    {
        return indices.size();
    }

    private void addVertex(Float x, Float y, Float z)
    {
        vertices.add(x);
        vertices.add(y);
        vertices.add(z);

        if (useHomogeneousCoordinates)
            vertices.add(1.0f);
    }

    private float[] toFloatArray(ArrayList<Float> floats)
    {
        float[] floatArray = new float[floats.size()];
        int i = 0;

        for (Float f : floats)
        {
            floatArray[i++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }

        return floatArray;
    }

    private int[] toIntArray(ArrayList<Integer> integers)
    {
        int[] intArray = new int[integers.size()];
        int i = 0;

        for (Integer num : integers)
        {
            intArray[i++] = (num != null ? num : 0); // Or whatever default you want.
        }

        return intArray;
    }

    private short[] toShortArray(ArrayList<Integer> integers)
    {
        short[] shortArray = new short[integers.size()];
        int i = 0;

        for (Integer num : integers)
        {
            shortArray[i++] = (num != null ? num.shortValue() : 0); // Or whatever default you want.
        }

        return shortArray;
    }


}
