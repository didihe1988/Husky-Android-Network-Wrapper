__author__ = 'lml'

from flask import Flask
from flask import request

app = Flask(__name__)


@app.route('/get_normal')
def get():
    return 'Hello World!'


@app.route('/post_normal', methods=['POST'])
def post():
    print('receive: '+str(request.form))
    return "eco form: "+str(request.form)


@app.route('/upload_file', methods=['POST'])
def post_file():
    print(request.form)
    image = request.files['test']
    filename = image.filename
    receive = 'receive: '+filename+' '
    print(filename)
    path = 'tmp/' + filename
    image.save(path)

    baidu = request.files['baidu']
    filename = baidu.filename
    receive += filename
    print(filename)
    path = 'tmp/' + filename
    baidu.save(path)

    return receive

if __name__ == '__main__':
    app.run(host='0.0.0.0')
