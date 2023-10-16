import json


class JsonDictionaryPersistence:

    def dump_dict(self, path, dictionary):
        with open(path, 'w') as file:
            json.dump(dictionary, file)

    def load_dict(self, path):
        with open(path, 'r') as file:
            return json.load(file)
