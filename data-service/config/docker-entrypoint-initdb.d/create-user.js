
if (!db.getUser('appuser')) {
    db.createUser({
      user:  'appuser',
      pwd: 'test123',
      roles: [{
        role: 'readWrite',
        db: 'testdb'
      }]
    });
}
