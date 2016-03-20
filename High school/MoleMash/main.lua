--MoleMash
--Authors: Khalob Cognata and Albert Garrow
--Theme: Halo (Xbox 360)

--Main Variables
local w = display.contentWidth/5
local h = display.contentHeight/5
local BKGD = display.newImage("bkgd.png",0,0)
local Spark = display.newImage("spark.png",400,200)
local TotalScore = 0
local TotalScoreText = display.newText("Current Score: " .. TotalScore, display.contentWidth/(5.4/4), display.contentHeight/8, native.systemFont, 24)
local backgroundMusic = audio.loadStream("MainSong.mp3")
local hitSound = audio.loadSound("hit.wav")
local missSound = audio.loadSound("miss.wav")
local gameoverSound = audio.loadSound("gameover.wav")

--Loads Background Music
audio.play(backgroundMusic)

--Main Functions 
local function randButton()
Spark.x = math.random ( w/2, display.contentWidth - (w/2))
Spark.y = math.random ( h/2, display.contentHeight - (100 + h/2))
end

function Add ( event )
audio.play(hitSound)
TotalScore = TotalScore + 1
TotalScoreText.text = "Current Score: " .. TotalScore
return true
end

function Subtract ( event )
audio.play(missSound)
TotalScore = TotalScore - 1
TotalScoreText.text = "Current Score: " .. TotalScore
return true
end

--Check the score to figure out if it should display victory/defeat
local function Finish (event)
	if TotalScore > 9 then
		BKGD.isVisible = false
		Spark.isVisible = false
		display.newImage("Victory.png")
		audio.stop()
		audio.play(gameoverSound)
	elseif TotalScore < -2 then
		BKGD.isVisible = false
		Spark.isVisible = false
		display.newImage("Defeat.png")
		audio.stop()
		audio.play(gameoverSound)
	end
end

--EventListeners and the timer
BKGD:addEventListener( "tap", Subtract )
BKGD:addEventListener( "tap", Finish )
Spark:addEventListener( "tap", Add )
Spark:addEventListener( "tap", Finish )
timer.performWithDelay(1000, randButton,0)