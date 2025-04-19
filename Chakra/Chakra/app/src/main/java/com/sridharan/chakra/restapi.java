package com.sridharan.chakra;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class restapi extends Fragment {
    private RecyclerView booksRecyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private TextView errorTextView;
    private RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restapi, container, false);

        // Initialize views
        booksRecyclerView = view.findViewById(R.id.booksRecyclerView);
        errorTextView = view.findViewById(R.id.errorTextView);

        // Initialize list and adapter
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookList);

        // Set up RecyclerView
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        booksRecyclerView.setAdapter(bookAdapter);

        // Initialize Volley
        requestQueue = Volley.newRequestQueue(requireContext());

        // Fetch books
        fetchBooks();

        return view;
    }

    private void fetchBooks() {
        String url = "http://192.168.6.151:8080/api/books";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    bookList.clear();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject bookJson = response.getJSONObject(i);
                            Book book = new Book(
                                    bookJson.getString("title"),
                                    bookJson.getString("author")
                            );
                            bookList.add(book);
                        }
                        bookAdapter.notifyDataSetChanged();
                        errorTextView.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        errorTextView.setText("Error parsing JSON: " + e.getMessage());
                        errorTextView.setVisibility(View.VISIBLE);
                    }
                },
                error -> {
                    errorTextView.setText("Error fetching books: " + error.getMessage());
                    errorTextView.setVisibility(View.VISIBLE);
                });

        requestQueue.add(jsonArrayRequest);
    }
}

// Book model class
class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}

// Book Adapter
class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorTextView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
        }
    }
}