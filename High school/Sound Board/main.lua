--       Chatper 6
--      Sound Board
-- author: Khalob Cognata

--multi-touch
system.activate("multitouch")
local ui = require("ui")

--background image
local bkgd = display.newImage("leesin.png",0,0)

--Define width/height
local w = (display.contentWidth/2) - 350
local h = (display.contentHeight/2) + 170

--Play/Resume buttons
local resumebtn = display.newImage("play.png", 100, display.contentHeight - 425)
local pausebtn = display.newImage("pause.png", 100, display.contentHeight - 425)
	pausebtn.isVisible = false


--Sounds
local firstblood = audio.loadSound("fb.mp3")
local welcome = audio.loadSound("welcome.mp3")
local doublekill = audio.loadSound("dbk.mp3")
local triplekill = audio.loadSound("tpk.mp3")
local quadrakill = audio.loadSound("qdk.mp3")
local pentakill = audio.loadSound("ptk.mp3")
local ace = audio.loadSound("ace.mp3")

--Main background music
local the_legend = audio.loadStream("thelegend.mp3")

--Button Press events
local playfb = function(event)
	audio.play(firstblood)
end

local playwelcome = function(event)
	audio.play(welcome)
end

local playdouble = function(event)
	audio.play(doublekill)
end

local playtriple = function(event)
	audio.play(triplekill)
end

local playquadra = function(event)
	audio.play(quadrakill)
end

local playpenta = function(event)
	audio.play(pentakill)
end

local playace = function(event)
	audio.play(ace)
end

local function resume()
	audio.play(the_legend)
	pausebtn.isVisible = true
	resumebtn.isVisible = false
end

local function pause()
	audio.stop()
    pausebtn.isVisible = false
    resumebtn.isVisible = true
end


--Buttons
local fbButton = ui.newButton{
	default = "fb.png",
	over = "fb1.png",
	onPress = playfb,
	text = " FB",
	size = 18,
	emboss = true
	}	
	
fbButton.x = w 
fbButton.y = h

local aceButton = ui.newButton{
	default = "ace.png",
	over = "ace1.png",
	onPress = playace,
	text = " Ace",
	size = 16,
	emboss = true
	}	
	
aceButton.x = w + 100
aceButton.y = h

local welcomeButton = ui.newButton{
	default = "welcomebutton.png",
	onPress = playwelcome,
	over = "welcomebutton1.png"
	}	
	
welcomeButton.x = w + 200
welcomeButton.y = h

local dbkButton = ui.newButton{
	default = "double-kill.png",
	onPress = playdouble,
	over = "double-kill1.png"
	}	
	
dbkButton.x = w + 300
dbkButton.y = h

local tpkButton = ui.newButton{
	default = "triple-kill.png",
	onPress = playtriple,
	over = "triple-kill1.png"
	}	
	
tpkButton.x = w + 400
tpkButton.y = h

local qdkButton = ui.newButton{
	default = "quadra-kill.png",
	onPress = playquadra,
	over = "quadra-kill1.png"
	}	
	
qdkButton.x = w + 500
qdkButton.y = h

local ptkButton = ui.newButton{
	default = "penta-kill.png",
	onPress = playpenta,
	over = "penta-kill1.png"
	}	
	
ptkButton.x = w + 600
ptkButton.y = h


--Event Listeners
resumebtn:addEventListener("tap", resume)
pausebtn:addEventListener("tap", pause)