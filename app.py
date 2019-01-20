import os, bcrypt
from datetime import datetime
from flask import Flask, request, jsonify, render_template
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__, static_folder='.')
app.config['UPLOAD_FOLDER'] = 'uploads'
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///app.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

class User(db.Model):
	id = db.Column(db.Integer, primary_key=True)
	username = db.Column(db.String(20), unique=True, nullable=False)
	email = db.Column(db.String(120), unique=True, nullable=False)
	password = db.Column(db.BINARY(60), nullable=False)
	posts = db.relationship('Post', backref='author', lazy=True)

	def __repr__(self):
		return f'User({self.username}, {self.email})'

class Post(db.Model):
	id = db.Column(db.Integer, primary_key=True)
	type = db.Column(db.Integer, nullable=False)
	date = db.Column(db.DateTime, nullable=False, default=datetime.utcnow)
	position = db.Column(db.String(), nullable=False)
	description = db.Column(db.Text, nullable=False)
	user_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)

	def __repr__(self):
		return f'Post({type}, {self.date})'

	def serialize(self):
		return {
				'id': self.id,
				'type': self.type,
				'date': self.date,
				'position': self.position,
				'description': self.description,
				'user_id': self.user_id
		}

def path(id):
	return os.path.join(app.config['UPLOAD_FOLDER'], str(id) + '.jpg')

@app.route('/')
def hello():
	return 'Hello World!'

@app.route('/user', methods=['POST'])
def user():
	if 'id' not in request.form:
		return 'id missing', 400
	user = User.query.filter_by(id=request.form['id']).first()
	if user == None:
		return 'inexistant', 404
	return user.username

@app.route('/data', methods=['GET', 'POST', 'DELETE'])
def data():
	if request.method == 'POST':
		for key in ['type', 'position', 'description', 'user_id']:
			if request.form.get(key) == None:
				return key + ' missing', 400
		if 'image' not in request.files:
			return 'image missing', 400
		file = request.files['image']
		if file.filename == '':
			return 'image missing', 400
		post = Post(
			type = request.form['type'],
			position = request.form['position'],
			description = request.form['description'],
			user_id = request.form['user_id']
		)
		db.session.add(post)
		db.session.flush()
		file.save(path(post.id))
		db.session.commit()
		return jsonify(post.serialize())
	elif request.method == 'DELETE':
		if 'id' not in request.form:
			return 'id missing', 400
		id=request.form['id']
		Post.query.filter_by(id=id).delete()
		db.session.commit()
		file = path(id)
		if os.path.exists(file):
			os.remove(file)
		return 'ok'
	if request.args.get('form') != None:
		return app.send_static_file('app.html')
	if request.args.get('id') != None:
		post = Post.query.filter_by(id=request.args['id']).first()
		if post == None:
			return 'not found', 404
		return jsonify(post.serialize())
	return jsonify([post.serialize() for post in Post.query.all()])
	# if neither, 405 ou 406

if __name__ == '__main__':
	app.run(debug = True)
