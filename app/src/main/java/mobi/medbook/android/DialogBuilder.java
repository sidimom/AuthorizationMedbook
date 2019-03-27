package mobi.medbook.android;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class DialogBuilder {


    public static Dialog buildInfoDialog(Context ctx, String title, String description) {
        final Dialog dialog = buildDefaultDialog(ctx, R.layout.dialog);

        TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
        TextView dialogText = dialog.findViewById(R.id.dialog_text);
        TextView dialogAcceptButton = dialog.findViewById(R.id.dialog_accept_button);
        TextView dialogCancelButton = dialog.findViewById(R.id.dialog_cancel_button);

        dialogTitle.setText(title == null ? "" : title);
        dialogText.setText(description == null ? "" : description);
        if (description == null) {
            dialogText.setVisibility(View.GONE);
        }

        View.OnClickListener onClick;
        dialogCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogAcceptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static Dialog buildDefaultDialog(@NonNull Context context, int contentViewId) {

        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        View view = LayoutInflater.from(context).inflate(contentViewId, null, false);

        dialog.setContentView(view);
        dialog.setCancelable(false);
        return dialog;
    }
}
