__author__ = 'lml'

from flask import Flask
from flask import request
app = Flask(__name__)

@app.route('/get_method')
def get():
    return 'Hello World!'

@app.route('/post_method',methods=['POST'])
def post():
    print(request.form);
    return str(request.form)

if __name__ == '__main__':
    app.run(host='0.0.0.0')
