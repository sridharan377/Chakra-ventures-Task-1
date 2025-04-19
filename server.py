from flask import Flask, jsonify

app = Flask(__name__)

books = [
    {"id": 1, "title": "The Great Gatsby", "author": "F. Scott Fitzgerald"},
    {"id": 2, "title": "1984", "author": "George Orwell"},
    {"id": 3, "title": "To Kill a Mockingbird", "author": "Harper Lee"},
    {"id": 4, "title": "Pride and Prejudice", "author": "Jane Austen"},
    {"id": 5, "title": "The Catcher in the Rye", "author": "J.D. Salinger"},
    {"id": 6, "title": "Moby-Dick", "author": "Herman Melville"},
    {"id": 7, "title": "Brave New World", "author": "Aldous Huxley"},
    {"id": 8, "title": "Jane Eyre", "author": "Charlotte Bronte"},
    {"id": 9, "title": "Animal Farm", "author": "George Orwell"},
    {"id": 10, "title": "The Hobbit", "author": "J.R.R. Tolkien"},

]

@app.route('/api/books', methods=['GET'])
def get_books():
    return jsonify(books)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)


