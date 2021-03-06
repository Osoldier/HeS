package me.it.hes.space.galaxy;

import me.it.lib.graphics.*;
import me.it.lib.math.*;

public class GalaxyShader extends Shader {

	private static final String VERTEX_FILE = "src/me/it/hes/space/galaxy/g.vert";
	private static final String FRAGMENT_FILE = "src/me/it/hes/space/galaxy/g.frag";
	private ModelMatrix ml_mat;
	private ViewMatrix vw_mat;
	private ProjectionMatrix pr_mat;
	
	private int ml_loc, vw_loc, pr_loc;
	
	public GalaxyShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		setMl_mat(new ModelMatrix());
		this.ml_loc = this.getUniformLocation("ml_mat");
		this.vw_loc = this.getUniformLocation("vw_mat");
		this.pr_loc = this.getUniformLocation("pr_mat");
	}

	@Override
	public void loadUniforms() {
		this.setUniform(ml_loc, ml_mat);
	}
	
	
	public void loadOnceUniforms() {
		this.setUniform(pr_loc, pr_mat);
		this.setUniform(vw_loc, vw_mat);
	}
	
	public ModelMatrix getMl_mat() {
		return ml_mat;
	}

	public void setMl_mat(ModelMatrix ml_mat) {
		this.ml_mat = ml_mat;
	}

	public ViewMatrix getVw_mat() {
		return vw_mat;
	}

	public void setVw_mat(ViewMatrix vw_mat) {
		this.vw_mat = vw_mat;
	}

	public ProjectionMatrix getPr_mat() {
		return pr_mat;
	}

	public void setPr_mat(ProjectionMatrix pr_mat) {
		this.pr_mat = pr_mat;
	}
}
