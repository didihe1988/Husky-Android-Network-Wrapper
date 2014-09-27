__author__ = 'lml'

from flask import Flask
from flask import request

app = Flask(__name__)


@app.route('/get_normal')
def get():
    return 'Hello World!'


@app.route('/post_normal', methods=['POST'])
def post():
    print(request.form);
    return str(request.form)


@app.route('/upload_file', methods=['POST'])
def post_file():
    print(request.form)
    image = request.files['image']
    filename = image.filename
    print(filename)
    path = 'tmp/' + filename
    print(path)
    image.save(path)

    beauty = request.files['beauty']
    filename = beauty.filename
    print(filename)
    path = 'tmp/' + filename
    print(path)
    beauty.save(path)

    return 'success'

if __name__ == '__main__':
    app.run(host='0.0.0.0')
