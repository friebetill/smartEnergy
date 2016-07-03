import requests
import json

def constructApiUrl(street,houseNumber,city,country, postcode):
    app_id = "BdJCV1gAjGDkP2dixWRu"
    app_code = "MV0PdJIEhcOYveK3eILRUA"
    street = street.replace("ß", "ss")
    streetParts = street.split(" ")
    apiStreet = ""
    for str in streetParts:
        apiStreet += str + "%20"
    return  "geocode.json?housenumber="+ houseNumber + "&street=" + apiStreet + "&city=" + city + "&postalCode="+ postcode + "&country=" + country + "&gen=8" + "&app_id=" + app_id + "&app_code=" + app_code;

def add_addr(street, city, country, zip_code):
    our_server = "http://54.93.34.46"
    name =["Brett Bowman","Traci Morris","Henry Hill","Nathan Jacobs","Luke Carpenter","Jaime Reyes","Devin Pearson","Angelina Romero","Clint Andrews" , "Michele Arnold", "Kyong Levy",
            "Darcy Torchia",
            "Terese Sifuentes",
            "Willie Waltrip",
            "Elenor Bonebrake",
            "Jazmin Slough",
            "Bev Brugman",
            "Jackson Dejarnette",
            "Obdulia Scheu",
            "Lauryn Bellefeuille",
            "Merrilee Fulcher",
            "Narcisa Fallis",
            "Merle Roe",
            "Nerissa Baumgartner",
            "Nicolette Mares",
            "Patria Tarver",
            "Lashaun Line",
            "Samira Zepp",
            "Celesta Blais",
            "Carlee Febus",
            "Sybil Hempel",
            "Fran Mcclendon",
            "Randy Mccallie",
            "Annabell Fenderson",
            "Bridgette Chiasson",
            "Una Hobson",
            "Ollie Shropshire",
            "Isiah Petersen",
            "Emelia Eastman",
            "Raphael Boysen",
            "Chandra Sankey",
            "Myrtle Bohl",
            "Leone Old",
            "Sherri Ort",
            "Diedre Grabert",
            "Kiley Stock",
            "Maryjo Coppage",
            "Lilian Hussein",
            "Mammie Holst",
            "Eleanore Pinegar",
            "Elayne Bordner",
            "Cayla Hayman",
            "Ahmad Gaymon" ]
    for i in range(1,len(name)):
        r = requests.get("https://geocoder.cit.api.here.com/6.2/"+constructApiUrl(street, str(i), "Berlin", "Germany",zip_code))

        print(r)

        res = r.json()
        lat = res["Response"]["View"][0]["Result"][0]["Location"]["DisplayPosition"]["Latitude"]
        lng = res["Response"]["View"][0]["Result"][0]["Location"]["DisplayPosition"]["Longitude"]


        headers = {"Content-Type":"application/json"}
        print(name[i])
        print("lat"+str(lat))
        print("lng"+str(lng))

        payload = {"name":name[i],"type":"client"}
        r = requests.post(our_server+"/users/", data=json.dumps(payload), headers=headers)
        print(r)
        res = r.json()
        user_id = res["id"]

        payload = {"name":name[i],"street":street, "postcode":zip_code,"city":city,"country":country,"floor":0,"location":{"lat":lat,"lng":lng}}
        r = requests.post(our_server+"/users/"+str(user_id)+"/addresses/", data=json.dumps(payload), headers=headers)
        print(r)


def main():


    street = "Charlottenstrasse"
    city = "Berlin"
    country = "Germany"

    add_addr("Charlottenstrasse", city, country, "10969")
    add_addr("Alte Jakobstraße", city, country, "10969")
    add_addr("Marktgrafenstraße", city, country, "10969")
    add_addr("Rudi-Dutschke-Straße",city, country, "10969")



if __name__ == "__main__":
    main()
