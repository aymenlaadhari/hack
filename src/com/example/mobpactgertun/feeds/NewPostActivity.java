package com.example.mobpactgertun.feeds;
import java.io.ByteArrayOutputStream;
import java.io.File;

import com.example.mobpactgertun.R;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@SuppressLint("NewApi")
public class NewPostActivity extends Activity {

	ProgressDialog prgDialog;
	String encodedString;
	RequestParams params = new RequestParams();
	String imgPath, fileName;
	Bitmap bitmap;
	private static int RESULT_LOAD_IMG = 1;
	   // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private Uri fileUri; // file url to store image/video
	ImageView imgView;

	private static final String TAG = NewPostActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_post);
		prgDialog = new ProgressDialog(this);
		// Set Cancelable as False
		prgDialog.setCancelable(false);
		ImageButton loadImage = (ImageButton)findViewById(R.id.buttonImage);
		ImageButton camImage = (ImageButton)findViewById(R.id.buttonCam);
		ImageButton send = (ImageButton)findViewById(R.id.buttonSend);
		imgView = (ImageView) findViewById(R.id.imgLoaded);
		loadImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadImagefromGallery(v);
			}
		});
		
		camImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				captureImage();
			}
		});

		
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				uploadImage(v);
				
				
				
			}
		});
	
		
	}

	public void loadImagefromGallery(View view) {
		// Create intent to Open Image applications like Gallery, Google Photos
		Intent galleryIntent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// Start the Intent
		startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
	}

	 /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
  
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
 
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
 
	
	// When Image is selected from Gallery
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			
			 if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
		            if (resultCode == RESULT_OK) {
		                
		            	// successfully captured the image
		                // launching upload activity
//		            	launchUploadActivity(true);
		            	// bimatp factory
		    			BitmapFactory.Options options = new BitmapFactory.Options();

		    			// down sizing image as it throws OutOfMemory Exception for larger
		    			// images
		    			options.inSampleSize = 8;

		    			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
		    			imgView.setImageBitmap(bitmap);
		            	
		            	
		            	
		            } else if (resultCode == RESULT_CANCELED) {
		                
		            	// user cancelled Image capture
		                Toast.makeText(getApplicationContext(),
		                        "User cancelled image capture", Toast.LENGTH_SHORT)
		                        .show();
		            
		            } else {
		                // failed to capture image
		                Toast.makeText(getApplicationContext(),
		                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
		                        .show();
		            }
		        
		        }
			
			// When an Image is picked
			if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
					&& null != data) {
				// Get the Image from data

				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				// Get the cursor
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				// Move to first row
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				imgPath = cursor.getString(columnIndex);
				cursor.close();
				
				// Set the Image in ImageView
				BitmapFactory.Options options = new BitmapFactory.Options();

    			// down sizing image as it throws OutOfMemory Exception for larger
    			// images
    			options.inSampleSize = 8;
    			final Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
    			imgView.setImageBitmap(bitmap);
				// Get the Image's file name
				String fileNameSegments[] = imgPath.split("/");
				fileName = fileNameSegments[fileNameSegments.length - 1];
				// Put file name in Async Http Post Param which will used in Php web app
				params.put("filename", fileName);

			} else {
				Toast.makeText(this, "You haven't picked Image",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
					.show();
		}

	}
	
	
	
	// When Upload button is clicked
	public void uploadImage(View v) {
		// When Image is selected from Gallery
		if (imgPath != null && !imgPath.isEmpty()) {
			prgDialog.setMessage("Converting Image to Binary Data");
			prgDialog.show();
			// Convert image to String using Base64
			encodeImagetoString();
		// When Image is not selected from Gallery
		} else {
			Toast.makeText(
					getApplicationContext(),
					"You must select image from gallery before you try to upload",
					Toast.LENGTH_LONG).show();
		}
	}

	// AsyncTask - To convert Image to String
	public void encodeImagetoString() {
		new AsyncTask<Void, Void, String>() {

			protected void onPreExecute() {

			};

			@Override
			protected String doInBackground(Void... params) {
				BitmapFactory.Options options = null;
				options = new BitmapFactory.Options();
				options.inSampleSize = 3;
				bitmap = BitmapFactory.decodeFile(imgPath,
						options);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				// Must compress the Image to reduce image size to make upload easy
				bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream); 
				byte[] byte_arr = stream.toByteArray();
				// Encode Image to String
				encodedString = Base64.encodeToString(byte_arr, 0);
				return "";
			}

			@Override
			protected void onPostExecute(String msg) {
				prgDialog.setMessage("Calling Upload");
				// Put converted Image string into Async Http Post param
				params.put("image", encodedString);
				// Trigger Image upload
				triggerImageUpload();
			}
		}.execute(null, null, null);
	}
	
	public void triggerImageUpload() {
		makeHTTPCall();
	}

	// http://192.168.2.4:9000/imgupload/upload_image.php
	// http://192.168.2.4:9999/ImageUploadWebApp/uploadimg.jsp
	// Make Http call to upload Image to Php server
	public void makeHTTPCall() {
		prgDialog.setMessage("Invoking Php");		
		AsyncHttpClient client = new AsyncHttpClient();
		// Don't forget to change the IP address to your LAN address. Port no as well.
		client.post("http://deviantsart.com",
				params, new AsyncHttpResponseHandler() {
					// When the response returned by REST has Http
					// response code '200'
					@Override
					public void onSuccess(String response) {
						// Hide Progress Dialog
						prgDialog.hide();
						Toast.makeText(getApplicationContext(), response,
								Toast.LENGTH_LONG).show();
						finish();
					}

					// When the response returned by REST has Http
					// response code other than '200' such as '404',
					// '500' or '403' etc
					@Override
					public void onFailure(int statusCode, Throwable error,
							String content) {
						// Hide Progress Dialog
						prgDialog.hide();
						// When Http response code is '404'
						if (statusCode == 404) {
							Toast.makeText(getApplicationContext(),
									"Requested resource not found",
									Toast.LENGTH_LONG).show();
						}
						// When Http response code is '500'
						else if (statusCode == 500) {
							Toast.makeText(getApplicationContext(),
									"Something went wrong at server end",
									Toast.LENGTH_LONG).show();
						}
						// When Http response code other than 404, 500
						else {
							Toast.makeText(
									getApplicationContext(),
									"Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
											+ statusCode, Toast.LENGTH_LONG)
									.show();
						}
					}
				});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// Dismiss the progress bar when application is closed
		if (prgDialog != null) {
			prgDialog.dismiss();
		}
	}


	/**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
 
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
 
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    
    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
 
    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {
 
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                util.Config.IMAGE_DIRECTORY_NAME);
 
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + util.Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
 
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
 
        return mediaFile;
    }
}