package com.jjemsboys.socialscience.studyapp.data

data class QA(val id: String, val q: String, val a: String)
data class MCQ(val q: String, val options: List<String>, val answer: Int)
data class TextbookQuestion(val id: String, val q: String, val a: String)

data class Chapter(
    val id: String,
    val title: String,
    val subject: String,
    val subtitle: String,
    val desc: String,
    val qa: List<QA>,
    val mcq: List<MCQ>,
    val textbook: List<TextbookQuestion>
)

object ChapterRepository {

    /**
     * Flattens every QA entry across every chapter into a single list.
     * Used by SearchScreen (global search) and SavedScreen (bookmarked items).
     */
    fun getAllQA(): List<QA> = chapters.flatMap { it.qa }

    val chapters = listOf(

        Chapter(
            id = "earth_living_planet",
            title = "The Earth – Our Living Planet",
            subject = "Geography",
            subtitle = "Chapter 11",
            desc = "Learn about the Earth, continents, oceans, latitudes, longitudes and time.",

            qa = listOf(

                QA("11.1","Where are we living?","We are living on the Earth. It is the third planet from the Sun."),

                QA("11.2","Why is Earth called a home for life?","Because of its suitable distance from the Sun, range of temperature, life-supporting gases, atmosphere, water cycle etc."),

                QA("11.3","What are the other names of the Earth?","Living Planet, Unique Planet, Watery Planet, Blue Planet."),

                QA("11.4","What is the rank of Earth in size in the solar system?","Earth is the fifth largest planet in the Sun's family."),

                QA("11.5","How does Earth's diameter compare with Moon and Sun?","Earth's diameter is approximately 4 times greater than the Moon and around 107 times less than that of the Sun."),

                QA("11.6","What is the total geographical area of Earth?","510 million sq kms."),

                QA("11.7","How much area is covered by water and land?","Water – 361 million sq kms (70.78%). Land – 149 million sq kms (29.22%)."),

                QA("11.8","What is the land-water ratio?","1 : 2.43"),

                QA("11.9","What is the shape of the Earth called?","Geoid or Oblate Spheroid."),

                QA("11.10","Why is Earth called a Geoid?","Because it is flattened at the poles and bulged at the equator."),

                QA("11.11","What are the diameters of Earth?","Equatorial diameter – 12,756 kms. Polar diameter – 12,714 kms."),

                QA("11.12","What are the circumferences of Earth?","Equatorial circumference – 40,076 kms. Polar circumference – 40,008 kms."),

                QA("11.13","What is the proof for Earth being a Geoid?","The difference of 42 kms between equatorial and polar diameter."),

                QA("11.14","What are continents?","The large land bodies of the Earth are known as continents."),

                QA("11.15","How many continents are there? Name them.","Seven – Asia, Africa, North America, South America, Antarctica, Europe and Australia."),

                QA("11.16","Which is the largest and smallest continent?","Largest – Asia. Smallest – Australia."),

                QA("11.17","What are oceans?","The large water bodies of the Earth are called oceans."),

                QA("11.18","How many major oceans are there? Name them.","Four – Pacific Ocean, Atlantic Ocean, Indian Ocean and Arctic Ocean."),

                QA("11.19","Which is the largest/deepest and smallest/shallowest ocean?","Largest and deepest – Pacific Ocean. Smallest and shallowest – Arctic Ocean."),

                QA("11.20","How is land and water distributed in hemispheres?","Northern Hemisphere – 60% land and 40% water, therefore called the Land Hemisphere. Southern Hemisphere – 81% water and 19% land, therefore called the Water Hemisphere."),

                QA("11.21","Why do we need latitudes and longitudes?","Because Earth is spherical, a network of lines is drawn to locate places and find direction and distance."),

                QA("11.22","What are latitudes and longitudes?","Horizontal lines are latitudes and vertical lines are longitudes. They intersect at right angles and form a grid or graticule."),

                QA("11.23","What is latitude?","An imaginary line joining places having the same angular distance north or south of the Equator. It is measured in degrees."),

                QA("11.24","Why are latitudes called parallels?","Because all lines of latitude are circles and remain parallel to the Equator."),

                QA("11.25","What is special about the Equator?","It is 0° latitude, the longest latitude and is called the Great Circle."),

                QA("11.26","How many latitudes are there?","There are 90° on each side of the Equator. Including the Equator, there are 181 latitudes."),

                QA("11.27","What is the distance between two degrees of latitude?","Approximately 110.4 kilometres."),

                QA("11.28","What are the important latitudes?","0° – Equator, 23°30' N – Tropic of Cancer, 23°30' S – Tropic of Capricorn, 66°30' N – Arctic Circle, 66°30' S – Antarctic Circle, 90° N – North Pole, 90° S – South Pole."),

                QA("11.29","What is longitude?","Imaginary lines joining the North Pole and South Pole that intersect the Equator at right angles."),

                QA("11.30","How are longitudes shown on the globe?","As a series of semi-circles running from pole to pole through the Equator."),
                              QA("11.31","Why are longitudes called Meridians?","'Meri' means mid and 'dian' means day because all places on the same meridian experience noon at the same time."),

                QA("11.32","What is the Prime Meridian?","The meridian passing through Greenwich (England), marked as 0° longitude."),

                QA("11.33","How many longitudes are there?","180° to the east and 180° to the west of Greenwich. Altogether there are 360 longitudes."),

                QA("11.34","What are the Eastern and Western Hemispheres?","The area between the Prime Meridian and 180°E is the Eastern Hemisphere, while the opposite area is the Western Hemisphere."),

                QA("11.35","What is the distance between two longitudes?","It decreases towards the poles because meridians converge. At the Equator it is about 111 km."),

                QA("11.36","What is the relation between longitude and time?","Earth rotates 360° in 24 hours. Therefore 1° = 4 minutes and 15° = 1 hour."),

                QA("11.37","How is time calculated east and west of GMT?","East – Gain – Add (E.G.A). West – Lose – Subtract (W.L.S)."),

                QA("11.38","What is Local Time?","The time according to the longitude of a place or the position of the Sun. Places on the same meridian have the same local time."),

                QA("11.39","What is Standard Time?","The uniform time followed throughout a country based on its central meridian to avoid confusion."),

                QA("11.40","What is Indian Standard Time (IST)?","IST is based on 82°30' East longitude passing through Prayagraj, Uttar Pradesh. It is 5 hours 30 minutes ahead of GMT."),

                QA("11.41","What are Time Zones?","The Earth is divided into 24 time zones, each differing by one hour."),

                QA("11.42","Give examples of countries with multiple time zones.","Russia – 11 time zones; USA and Canada – 5 time zones; Australia – 3 time zones."),

                QA("11.43","What is the International Date Line (IDL)?","A line passing through the 180° meridian in the Pacific Ocean where the date changes when crossed."),

                QA("11.44","Why was the International Date Line created?","To solve the problem of maintaining the correct date and day during travel around the world.")

            ),

            mcq = listOf(
              MCQ("What is the third planet from the Sun?", listOf("Mercury", "Venus", "Earth", "Mars"), 2),

MCQ("Earth is known as the?", listOf("Red Planet", "Blue Planet", "Ringed Planet", "Gas Planet"), 1),

MCQ("Earth is the _____ largest planet in the Solar System.", listOf("Third", "Fourth", "Fifth", "Sixth"), 2),

MCQ("How much of Earth's surface is covered by water?", listOf("50%", "60%", "70.78%", "80%"), 2),

MCQ("The shape of the Earth is called?", listOf("Sphere", "Cube", "Geoid", "Cylinder"), 2),

MCQ("Which is the largest continent?", listOf("Africa", "Europe", "Asia", "Australia"), 2),

MCQ("Which is the smallest continent?", listOf("Europe", "Australia", "Antarctica", "South America"), 1),

MCQ("Which is the largest ocean?", listOf("Atlantic Ocean", "Indian Ocean", "Pacific Ocean", "Arctic Ocean"), 2),

MCQ("The Northern Hemisphere is also called?", listOf("Water Hemisphere", "Land Hemisphere", "Blue Hemisphere", "Eastern Hemisphere"), 1),

MCQ("0° Latitude is called?", listOf("Prime Meridian", "Equator", "Arctic Circle", "Tropic of Cancer"), 1),

MCQ("Which latitude is known as the Tropic of Cancer?", listOf("23°30' North", "23°30' South", "66°30' North", "0°"), 0),

MCQ("The Prime Meridian passes through?", listOf("India", "USA", "Greenwich", "Japan"), 2),

MCQ("How many longitudes are there?", listOf("180", "181", "360", "365"), 2),

MCQ("Earth rotates 1° in?", listOf("2 minutes", "4 minutes", "10 minutes", "15 minutes"), 1),

MCQ("Indian Standard Time is based on?", listOf("90°E", "82°30'E", "75°E", "0°"), 1),

MCQ("IST is ahead of GMT by?", listOf("4 hours", "5 hours", "5 hours 30 minutes", "6 hours"), 2),

MCQ("The International Date Line passes near?", listOf("0°", "90°", "180°", "270°"), 2),

MCQ("Which country has 11 time zones?", listOf("India", "Russia", "Australia", "Canada"), 1),

MCQ("The distance between two degrees of latitude is about?", listOf("90 km", "100 km", "110.4 km", "150 km"), 2),

MCQ("The network of latitudes and longitudes is called?", listOf("Grid or Graticule", "Atlas", "Map Scale", "Compass"), 0)
            ),

            textbook = listOf(

                TextbookQuestion(
                    "TB11.1",
                    "Why is Earth called the Living Planet?",
                    "Earth is called the Living Planet because it has a suitable distance from the Sun, life-supporting gases, atmosphere, water cycle and favourable temperature, making it the only known planet that supports life."
                ),

                TextbookQuestion(
                    "TB11.2",
                    "Explain the shape and size of the Earth.",
                    "Earth is the fifth largest planet in the Solar System. It has a Geoid or Oblate Spheroid shape because it is slightly flattened at the poles and bulged at the equator. Its total geographical area is about 510 million square kilometres."
                ),

                TextbookQuestion(
                    "TB11.3",
                    "Name the continents and oceans of the Earth.",
                    "The seven continents are Asia, Africa, North America, South America, Antarctica, Europe and Australia. The four major oceans are the Pacific Ocean, Atlantic Ocean, Indian Ocean and Arctic Ocean."
                ),

                TextbookQuestion(
                    "TB11.4",
                    "What are latitudes and longitudes? Why are they important?",
                    "Latitudes are imaginary horizontal lines and longitudes are imaginary vertical lines drawn on the Earth. Together they form a grid or graticule that helps locate places, measure distance and determine direction."
                ),

                TextbookQuestion(
                    "TB11.5",
                    "Explain IST and the International Date Line.",
                    "Indian Standard Time (IST) is based on 82°30' East longitude passing through Prayagraj and is 5 hours 30 minutes ahead of GMT. The International Date Line lies near the 180° meridian in the Pacific Ocean and is used to change the calendar date while travelling around the world."
                )

            )

        ),
              Chapter(
            id = "sources",
            title = "Sources of History",
            subject = "History",
            subtitle = "Chapter 1",
            desc = "Learn about the different sources used to study and reconstruct history.",

            qa = listOf(

                QA("1.1","What are Sources?","Sources are the basic materials used to construct history. They provide information about the lives of people in the past. Historians analyse sources to write history."),

                QA("1.2","Give examples of sources.","Agricultural tools, temples, forts, inscriptions, coins, weapons, written documents and oral literature."),

                QA("1.3","What are the three main types of sources?","Literary Sources, Archaeological Sources, and Oral Sources and Legends."),

                QA("1.4","What are Literary Sources?","Literary sources are written records that help us know about the life, culture and history of people."),

                QA("1.5","What are the two types of Literary Sources?","Native Literature and Foreign Literature."),

                QA("1.6","What is Native Literature?","Native literature is literature written by Indians in languages such as Sanskrit, Kannada, Tamil, Telugu and Marathi."),

                QA("1.7","Give examples of Native Literature.","Mudrarakshasa, Rajatarangini, Buddha Charita, Arthashastra, Gathasaptashati, Harsha Charitha, Prithviraja Raso, Vikramarjuna Vijaya, Sangam Literature, Tripitikas and Kavirajamarga."),

                QA("1.8","What is Foreign Literature?","Foreign literature is literature written by foreign travellers and scholars describing India."),

                QA("1.9","Give examples of Foreign Literature.","Indica, Si-Yu-Ki, Gho-Kho-Ki, Deepavamsha, Mahavamsha, Geography by Ptolemy, Tarikh-E-Firoz Shahi and the writings of Nuniz, Barbosa and Nicolo Conti."),

                QA("1.10","What are Archaeological Sources?","Archaeological sources are objects such as inscriptions, coins, monuments and ruins discovered through excavation."),

                QA("1.11","What are Inscriptions?","Inscriptions are writings engraved on rocks, pillars or metal that provide valuable historical information."),

                QA("1.12","What is Numismatics?","Numismatics is the study of coins."),

                QA("1.13","What are Monuments?","Monuments are buildings and structures that remain as evidence of India's cultural heritage."),

                QA("1.14","What is Excavation?","Excavation is the scientific digging of hidden historical remains."),

                QA("1.15","What are Oral Sources?","Oral sources are historical information passed from one generation to another through songs, poems and ballads."),
                              QA("1.16","What are Legends?","Legends are traditional stories related to people, places and customs. They are also called Sthala-Puranas and reflect social beliefs.")

            ),

            mcq = listOf(

                MCQ("What are the basic materials used to construct history?", listOf("Maps", "Sources", "Books", "Paintings"), 1),

                MCQ("Which of the following is a Literary Source?", listOf("Coin", "Temple", "Arthashastra", "Tool"), 2),

                MCQ("Who wrote Arthashastra?", listOf("Kalhana", "Kautilya", "Pampa", "Banabhatta"), 1),

                MCQ("Which book was written by Ashwaghosha?", listOf("Rajatarangini", "Buddha Charita", "Indica", "Tripitikas"), 1),

                MCQ("Who wrote Indica?", listOf("Fa-Hien", "Huien Tsang", "Megasthenes", "Barani"), 2),

                MCQ("The study of coins is called?", listOf("Epigraphy", "Numismatics", "Archaeology", "Excavation"), 1),

                MCQ("Inscriptions are generally engraved on?", listOf("Paper", "Wood", "Rocks and pillars", "Cloth"), 2),

                MCQ("The first Kannada inscription is?", listOf("Ashoka Edict", "Halmidi Inscription", "Maski", "Brahmagiri"), 1),

                MCQ("Scientific digging of historical remains is called?", listOf("Research", "Survey", "Excavation", "Exploration"), 2),

                MCQ("Harappa belongs to which civilization?", listOf("Stone Age", "Mauryan Empire", "Indus Valley Civilization", "Gupta Empire"), 2),

                MCQ("Which is an Oral Source?", listOf("Coin", "Temple", "Folk Song", "Inscription"), 2),

                MCQ("Legends are also called?", listOf("Puranas", "Sthala-Puranas", "Vedas", "Charitas"), 1),

                MCQ("Who issued seven types of gold coins?", listOf("Ashoka", "Samudragupta", "Harsha", "Kanishka"), 1),

                MCQ("Roman coins found in Bengaluru prove trade with?", listOf("China", "Rome", "Egypt", "Persia"), 1),

                MCQ("Which is NOT an archaeological source?", listOf("Coin", "Monument", "Inscription", "Newspaper"), 3),

                MCQ("Ashoka's inscriptions were mainly written in?", listOf("Kannada", "Prakrit", "English", "Tamil"), 1),

                MCQ("Which script was used in Ashoka's inscriptions?", listOf("Brahmi", "Devanagari", "Roman", "Arabic"), 0),

                MCQ("Bimbetka is related to?", listOf("Stone Age", "Gupta Period", "Mughal Period", "Indus Valley"), 0),

                MCQ("Which monument is a World Heritage Site?", listOf("Taj Mahal", "Modern Museum", "Parliament", "Secretariat"), 0),

                MCQ("Historians analyse sources to?", listOf("Construct history", "Write stories", "Create maps", "Draw pictures"), 0)

            ),

            textbook = listOf(

                TextbookQuestion(
                    "TB1",
                    "What are Sources?",
                    "Sources are the basic materials needed to construct history. They provide information about people's lives. Historians analyse them to write history."
                ),

                TextbookQuestion(
                    "TB2",
                    "Name the two kinds of Literary Sources.",
                    "The two kinds of literary sources are Native Literature and Foreign Literature."
                ),

                TextbookQuestion(
                    "TB3",
                    "What is Numismatics?",
                    "Numismatics is the study of coins. Coins provide information about religion, culture, administration, society and economy."
                ),

                TextbookQuestion(
                    "TB4",
                    "What is Excavation?",
                    "Excavation is the scientific digging of buried historical remains to discover evidence of the past."
                ),

                TextbookQuestion(
                    "TB5",
                    "What are Archaeological Sources?",
                    "Archaeological sources include inscriptions, coins, monuments, ruins and other objects discovered through excavation."

                )

            )

        )

    )
}