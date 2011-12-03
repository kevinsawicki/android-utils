/*
 * Copyright (c) 2011 Kevin Sawicki <kevinsawicki@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package com.github.kevinsawicki.android.utils;

import static android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH;
import static android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL;
import static android.speech.RecognizerIntent.EXTRA_MAX_RESULTS;
import static android.speech.RecognizerIntent.EXTRA_PROMPT;
import static android.speech.RecognizerIntent.EXTRA_RESULTS;
import static android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Speech to text utilities
 */
public class Speech {

	/**
	 * Does the device support speech recognition?
	 *
	 * @param context
	 * @return true if support available, false otherwise
	 */
	public static boolean supportsSpeechRecognition(Context context) {
		return context != null
				&& supportsSpeechRecognition(context.getPackageManager());
	}

	/**
	 * Does the device support speech recognition?
	 *
	 * @param packageManager
	 * @return true if support available, false otherwise
	 */
	public static boolean supportsSpeechRecognition(
			PackageManager packageManager) {
		return packageManager != null
				&& !packageManager.queryIntentActivities(
						new Intent(ACTION_RECOGNIZE_SPEECH), 0).isEmpty();
	}

	/**
	 * Create intent for speech recognition
	 *
	 * @return intent
	 */
	public static Intent createIntent() {
		return createIntent(null);
	}

	/**
	 * Create intent for speech recognition
	 *
	 * @param prompt
	 * @return intent
	 */
	public static Intent createIntent(String prompt) {
		return createIntent(prompt, 1);
	}

	/**
	 * Create intent for speech recognition
	 *
	 * @param prompt
	 * @param results
	 * @return intent
	 */
	public static Intent createIntent(String prompt, int results) {
		Intent intent = new Intent(ACTION_RECOGNIZE_SPEECH);
		if (prompt != null)
			intent.putExtra(EXTRA_PROMPT, prompt);
		intent.putExtra(EXTRA_LANGUAGE_MODEL, LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(EXTRA_MAX_RESULTS, results);
		return intent;
	}

	/**
	 * Get results from recognizer intent
	 *
	 * @param data
	 * @return non-null but possibly empty list of results
	 */
	public static List<String> getResults(Intent data) {
		if (data == null)
			return Collections.emptyList();
		List<String> results = data.getStringArrayListExtra(EXTRA_RESULTS);
		return results != null ? results : Collections.<String> emptyList();
	}

	/**
	 * Get result from recognizer intent
	 *
	 * @param data
	 * @return result, may be null
	 */
	public static String getResult(Intent data) {
		List<String> results = getResults(data);
		return !results.isEmpty() ? results.get(0) : null;
	}
}
