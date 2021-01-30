package com.example.cricketclubapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    CircleImageView imageView;
    Button closeButton, saveButton;
    TextView profileChange;
    private Uri imageUri;
    private String myUri = "";
    //Firebase storage
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imageView = findViewById(R.id.imageView7);
        closeButton = (Button) findViewById(R.id.buttonClose);
        saveButton = (Button) findViewById(R.id.buttonSave);
        profileChange = (TextView) findViewById(R.id.changeProfile);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getInstance().getReference().child("Profile Pic");
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
               /* Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(intent);*/
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPicture();
            }
        });

        profileChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAspectRatio(1,1).start(EditProfile.this);
            }
        });

        getUserInfo();

    }

    private void getUserInfo() {
        FirebaseFirestore.getInstance().collection("users").document(mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                if(value.getData().containsKey("image"))
                {
                    String image = value.get("image").toString();
                    Picasso.get().load(image).into(imageView);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            imageView.setImageURI(imageUri);
        }else{
            Toast.makeText(this, "Something wrong occured, try again!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPicture(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading your image....");
        pd.setMessage("Please wait, we are setting up your profile.");
        pd.show();

        if(imageUri != null){
            final StorageReference ref = storageReference.child(mAuth.getCurrentUser().getUid() + ".jpg");

            uploadTask = ref.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloadUrl = task.getResult();
                        myUri = downloadUrl.toString();

                        HashMap<String, Object> myMap = new HashMap<>();
                        myMap.put("image", myUri);

                        //databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(myMap);
                        FirebaseFirestore.getInstance().collection("users").document(mAuth.getCurrentUser().getUid()).update(myMap);
                        Toast.makeText(getApplicationContext(), "Image Uploaded.", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });
        }else{
            pd.dismiss();
            Toast.makeText(this, "Image not selected.", Toast.LENGTH_SHORT).show();
        }
    }
}


//uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//@Override
//public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
//        pd.dismiss();
//        Toast.makeText(getApplicationContext(), "Image Uploaded.", Toast.LENGTH_SHORT).show();
//        }
//        })
//        .addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        pd.dismiss();
//        Toast.makeText(getApplicationContext(), "Failed to upload.", Toast.LENGTH_SHORT).show();
//        }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//@Override
//public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//        double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
//        pd.setMessage((int)progressPercent + "% of your image has been uploaded.");
//        }
//        });