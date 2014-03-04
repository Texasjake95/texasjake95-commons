package com.texasjake95.commons.vector;

import java.io.Serializable;

public class Vector2D implements Serializable{
	
	private static final long serialVersionUID = 6479562263023348410L;
	protected float x;
	protected float y;
	
	public Vector2D()
	{
	}
	
	public Vector2D(float x)
	{
		this.x = x;
	}
	
	public Vector2D(float x, float y)
	{
		this(x);
		this.y = y;
	}
	
	public Vector2D addition(Vector2D vec)
	{
		float VecX = this.x + vec.x;
		float VecY = this.y + vec.y;
		return new Vector2D(VecX, VecY);
	}
	
	public Vector2D addition(Vector2D... vecs)
	{
		Vector2D current = new Vector2D();
		for (Vector2D vec : vecs)
			current = current.addition(vec);
		return current;
	}
	
	public Vector2D subtaction(Vector2D vec)
	{
		float VecX = this.x - vec.x;
		float VecY = this.y - vec.y;
		return new Vector2D(VecX, VecY);
	}
	
	public Vector2D subtaction(Vector2D... vecs)
	{
		Vector2D current = new Vector2D();
		for (Vector2D vec : vecs)
			current = current.subtaction(vec);
		return current;
	}
	
	public float dotProduct(Vector2D vec)
	{
		float VecX = vec.x * this.x;
		float VecY = vec.y * this.y;
		return VecX + VecY;
	}
	
	public float dotProduct(Vector2D... vecs)
	{
		float VecX = this.x;
		float VecY = this.y;
		for (Vector2D vec : vecs)
		{
			VecX *= vec.x;
			VecY *= vec.y;
		}
		return VecX + VecY;
	}
	
	@Override
	public String toString()
	{
		return x + ":i" + " " + y + ":j";
	}
	
	public float getMagnitude()
	{
		float Xsq = x * x;
		float Ysq = y * y;
		return (float) Math.sqrt(Xsq + Ysq);
	}
}
