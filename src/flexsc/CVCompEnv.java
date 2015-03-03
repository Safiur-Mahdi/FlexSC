// Copyright (C) 2014 by Xiao Shaun Wang <wangxiao@cs.umd.edu>
package flexsc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import util.Utils;

public class CVCompEnv extends BooleanCompEnv {
	public CVCompEnv(InputStream is, OutputStream os, Party p) {
		super(is, os, p, Mode.VERIFY);
		this.party = p;
	}

	public int numOfAnds = 0;
	@Override
	public Boolean inputOfAlice(boolean in) {
		Boolean res = null;
		try {
			res = in;
			if (party == Party.Alice)
				os.write(in ? 1 : 0);
			else {
				int re = is.read();
				res = re == 1;
			}
			flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		return res;
	}

	@Override
	public Boolean inputOfBob(boolean in) {
		Boolean res = null;
		try {
			os.flush();
			res = in;
			if (party == Party.Bob)
				os.write(in ? 1 : 0);
			else {
				int re = is.read();
				res = re == 1;
			}
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}

		return res;
	}

	@Override
	public boolean outputToAlice(Boolean out) {
		return out;
	}

	public boolean outputToBob(Boolean out) {
		return out;
	}

	@Override
	public Boolean and(Boolean a, Boolean b) {
		++Flag.sw.ands;
		return a && b;
	}

	@Override
	public Boolean xor(Boolean a, Boolean b) {
		return a ^ b;
	}

	@Override
	public Boolean not(Boolean a) {
		return !a;
	}

	public Boolean[] inputOfAlice(boolean[] in) {
		Boolean[] res = new Boolean[in.length];
		for (int i = 0; i < res.length; ++i)
			res[i] = inputOfAlice(in[i]);
		return res;
	}

	@Override
	public Boolean[] inputOfBob(boolean[] in) {
		Boolean[] res = new Boolean[in.length];
		for (int i = 0; i < res.length; ++i)
			res[i] = inputOfBob(in[i]);
		return res;
	}

	@Override
	public boolean[] outputToAlice(Boolean[] out) {
		return Utils.tobooleanArray(out);
	}

	@Override
	public boolean[] outputToBob(Boolean[] out) {
		return Utils.tobooleanArray(out);
	}
}
